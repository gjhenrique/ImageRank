package br.uel.mdd.utils;

import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.extractor.JFeatureLib;
import br.uel.mdd.extractor.ReducedScaleWaveletExtractor;
import de.lmu.ifi.dbs.jfeaturelib.features.AbstractFeatureDescriptor;
import math.jwave.transforms.wavelets.Wavelet;

public class ExtractorUtils {

    public static FeatureExtractor getFeatureExtractorImplementation(Extractors extractor) {

        Class clazzFeatureExtractor = ReflectionUtils.findClassByName(getExtractorsPackage(), extractor.getClassName());

        FeatureExtractor featureExtractor = null;
        if (clazzFeatureExtractor == ReducedScaleWaveletExtractor.class) {

            featureExtractor = createWaveletExtractor(extractor);

        } else if (clazzFeatureExtractor == JFeatureLib.class) {

            featureExtractor = createJFeatureLibExtractor(extractor);

        }

        if (featureExtractor == null)
            throw new RuntimeException("Extractor " + extractor + " does not have the proper constructor");

        return featureExtractor;
    }

    private static JFeatureLib createJFeatureLibExtractor(Extractors extractor) {
        Class clazzIdentifier = ReflectionUtils.findClassByName(getJFeatureLibPackage(), extractor.getTypeIdentifier());

        JFeatureLib featureExtractor = null;

        try {
            AbstractFeatureDescriptor abstractFeatureDescriptor = (AbstractFeatureDescriptor) clazzIdentifier.newInstance();
            featureExtractor = new JFeatureLib(abstractFeatureDescriptor);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return featureExtractor;
    }

    private static ReducedScaleWaveletExtractor createWaveletExtractor(Extractors extractor) {
        Class clazzFilter = ReflectionUtils.findClassByName(getFilterPackage(extractor), extractor.getTypeIdentifier());
        ReducedScaleWaveletExtractor featureExtractor = null;
        try {
            Wavelet wavelet = (Wavelet) clazzFilter.newInstance();
            featureExtractor = new ReducedScaleWaveletExtractor(extractor.getLevelsWavelet(), wavelet);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return featureExtractor;
    }

    private static String getExtractorsPackage() {
        return "br.uel.mdd.extractor";
    }


    private static String getFilterPackage(Extractors extractor) {
        String filterIdentifier = extractor.getTypeIdentifier();

        String packageName = "math.jwave.transforms.wavelets";

//        The wavelet Haar is already in the root of the package
        if (filterIdentifier.startsWith("Coiflet"))
            packageName += ".coiflet";
        else if (filterIdentifier.startsWith("Symlet"))
            packageName += ".symlets";
        else if (filterIdentifier.startsWith("Daubechies"))
            packageName += ".daubechies";

        return packageName;
    }

    private static String getJFeatureLibPackage() {
        return "de.lmu.ifi.dbs.jfeaturelib.features";
    }
}
