package br.uel.mdd.extractor;

import math.jwave.Transform;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.Wavelet;

import javax.inject.Inject;
import javax.inject.Named;

public class ReducedScaleWaveletExtractor implements FeatureExtractor {

    @Inject
    @Named("levels")
    private int levels;

    @Inject
    @Named("filter")
    private Wavelet filter;

    @Override
    public double[] extractFeature(double[][] pixels) {

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

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                subSpaceFeature[x * y] = transformedPixels[x][y];
            }
        }

        return subSpaceFeature;
    }
}
