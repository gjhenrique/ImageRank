package br.uel.mdd.utils;

public class PrimitiveUtils {

    public static Double[] castPrimitiveToWrapper(double[] features) {
        Double[] featuresContainer = new Double[features.length];

        for (int i = 0; i < features.length; i++) {
            featuresContainer[i] = features[i];
        }

        return featuresContainer;
    }

    public static Double[] castPrimitiveToWrapper(float[] features) {
        Double[] featuresContainer = new Double[features.length];

        for (int i = 0; i < features.length; i++) {
            featuresContainer[i] = (double) features[i];
        }

        return featuresContainer;
    }

    public static float[] castWrapperToPrimitive(Double[] features) {
        float[] featuresPrimitive = new float[features.length];

        for (int i = 0; i < features.length; i++) {
            featuresPrimitive[i] = features[i].floatValue();
        }

        return featuresPrimitive;
    }

}
