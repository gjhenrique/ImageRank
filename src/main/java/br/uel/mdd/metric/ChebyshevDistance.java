package br.uel.mdd.metric;

import br.uel.mdd.metric.MetricEvaluator;

public class ChebyshevDistance implements MetricEvaluator {
    @Override
    public double getDistance(double[] obj1, double[] obj2) {
        double max = 0;

        for(int i = 0; i < obj1.length; i++) {
            max = Math.max(max, Math.abs(obj1[i] - obj2[i]));
        }

        return max;
    }
}
