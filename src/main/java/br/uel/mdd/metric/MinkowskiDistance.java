package br.uel.mdd.metric;

import br.uel.mdd.utils.PrimitiveUtils;

public abstract class MinkowskiDistance implements MetricEvaluator {

    @Override
    public double getDistance(Double[] obj1, Double[] obj2) {

        if (obj1.length != obj2.length) {
            throw new IllegalArgumentException("Vetor of size " + obj1.length + " is different from " + obj2.length);
        }

        float[] wrapper1 = PrimitiveUtils.castWrapperToPrimitiveFloat(obj1);
        float[] wrapper2 = PrimitiveUtils.castWrapperToPrimitiveFloat(obj2);

        double value = 0;
        for (int i = 0; i < wrapper1.length; i++) {
            double absDiff = Math.abs(wrapper1[i] - wrapper2[i]);
            value += Math.pow(absDiff, order());
        }

//      It only works with positive values
        return Math.pow(value, 1.0 / order());
    }

    public abstract int order();
}
