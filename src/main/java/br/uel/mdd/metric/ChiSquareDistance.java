package br.uel.mdd.metric;

public class ChiSquareDistance implements MetricEvaluator {

    @Override
    public double getDistance(Double[] obj1, Double[] obj2) {

        double value = 0;
        for (int i = 0; i < obj1.length; i++) {
            double mean = (obj1[i] + obj2[i]) / 2;
            value += Math.pow((obj2[i] - mean), 2) / mean;
        }
        return value;
    }
}
