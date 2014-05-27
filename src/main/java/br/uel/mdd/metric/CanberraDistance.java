package br.uel.mdd.metric;

import br.uel.mdd.metric.MetricEvaluator;

public class CanberraDistance implements MetricEvaluator {

    @Override
    public double getDistance(double[] obj1, double[] obj2) {

        double value = 0;

        for (int i = 0; i < obj1.length; i++) {
           double diff = Math.abs(obj1[i] - obj2[i]);
           double sum = Math.abs(obj1[i]) + Math.abs(obj2[i]);
           value += diff / sum;
        }
        return value;
    }
}
