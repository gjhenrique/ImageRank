package br.uel.mdd.extractor;

import br.uel.mdd.io.ImageWrapper;
import math.jwave.Transform;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.Wavelet;

import javax.inject.Inject;
import javax.inject.Named;

public class ReducedScaleWaveletExtractor implements FeatureExtractor {

    private int levels;

    private Wavelet filter;

    @Inject
    public ReducedScaleWaveletExtractor(@Named("levels") int levels,
                                        @Named("filter") Wavelet wavelet) {
        this.levels = levels;
        this.filter = wavelet;
    }

    @Override
    public double[] extractFeature(ImageWrapper image) {

        double[][] pixels = image.getPixelMatrix();


        double[][] transformedPixels = this.performWaveletTransform(pixels);

        return this.calculateSubspace(transformedPixels);
    }

    private double[][] performWaveletTransform(double[][] pixels) {

        double[][] transformedWavelet = null;
        try {
            Transform transform = new Transform(new FastWaveletTransform(this.filter), this.levels);
            transformedWavelet = transform.forward(pixels);
        } catch (JWaveFailure jWaveFailure) {
            jWaveFailure.printStackTrace();
        }

        return transformedWavelet;
    }

    private double[] calculateSubspace(double[][] transformedPixels) {
        int levelsExp = (int) Math.pow(2, this.levels);

        int width = transformedPixels[0].length / levelsExp;
        int height = transformedPixels[1].length / levelsExp;

        double[] subSpaceFeature = new double[width * height];


        int k = 0;

        for (int x = height-1; x >= 0; x--) {
            for (int y = 0; y < width; y++) {
                subSpaceFeature[k++] = transformedPixels[x][y];
            }
        }
        return subSpaceFeature;
    }

    @Override
    public String toString() {
        return "ReducedScaleWaveletExtractor{" +
                "levels=" + levels +
                ", filter=" + filter +
                '}';
    }
}
