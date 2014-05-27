package br.uel.mdd.extractor;

import math.jwave.Transform;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.Haar1;
import math.jwave.transforms.wavelets.Wavelet;

public class ReducedScaleWaveletExtractor implements FeatureExtractor {

    private int levels;

    public ReducedScaleWaveletExtractor(int levels) {
        this.levels = levels;
    }

    @Override
    public double[] extractFeature(double[][] pixels) {

        double[][] transformedPixels = this.performWaveletTransform(pixels);

        return this.calculateSubspace(transformedPixels);
    }

    private double[][] performWaveletTransform(double[][] pixels) {

        double[][] transformedWavelet = null;
        try {
            Wavelet filter = new Haar1();
            Transform transform = new Transform(new FastWaveletTransform(filter), this.levels);
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

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                subSpaceFeature[x * y] = transformedPixels[x][y];
            }
        }

        return subSpaceFeature;
    }
}
