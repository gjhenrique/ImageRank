package br.uel.mdd.extractor;

import br.uel.mdd.io.ImageWrapper;
import math.jwave.Transform;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.Haar1;
import math.jwave.transforms.wavelets.Wavelet;
import math.jwave.transforms.wavelets.coiflet.*;
import math.jwave.transforms.wavelets.daubechies.*;
import math.jwave.transforms.wavelets.symlets.Symlets2;

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

        for (int x = height - 1; x >= 0; x--) {
            for (int y = 0; y < width; y++) {
                subSpaceFeature[k++] = transformedPixels[x][y];
            }
        }
        return subSpaceFeature;
    }

    @Override
    public String toString() {
        return getFilterName() + " - " + levels;
    }

    private String getFilterName() {

        String filterName = daubechiesString();

        if (filterName == null)
            filterName = coifletString();

        if (filterName == null)
            filterName = symletString();

        return filterName;
    }


    private String daubechiesString() {
        String filterName = null;
        if (filter instanceof Haar1)
            filterName = "db1";
        else if (filter instanceof Daubechies2)
            filterName = "db2";
        else if (filter instanceof Daubechies3)
            filterName = "db3";
        else if (filter instanceof Daubechies4)
            filterName = "db4";
        else if (filter instanceof Daubechies5)
            filterName = "db5";
        else if (filter instanceof Daubechies6)
            filterName = "db6";
        else if (filter instanceof Daubechies7)
            filterName = "db7";
        else if (filter instanceof Daubechies8)
            filterName = "db8";
        else if (filter instanceof Daubechies9)
            filterName = "db9";
        else if (filter instanceof Daubechies10)
            filterName = "db10";
        else if (filter instanceof Daubechies20)
            filterName = "db20";

        return filterName;
    }

    private String coifletString() {
        String filterName = null;

        if (filter instanceof Coiflet1)
            filterName = "coif1";
        else if (filter instanceof Coiflet2)
            filterName = "coif2";
        else if (filter instanceof Coiflet3)
            filterName = "coif3";
        else if (filter instanceof Coiflet4)
            filterName = "coif4";
        else if (filter instanceof Coiflet5)
            filterName = "coif5";

        return filterName;
    }

    private String symletString() {
        String filterName = null;

        if (filter instanceof Symlets2)
            filterName = "sym2";
        else if (filter instanceof Daubechies2)
            filterName = "sym20";

        return filterName;
    }
}
