package br.uel.mdd.metric;

public class JeffreyDivergence implements MetricEvaluator{

    @Override
    public double getDistance(Double[] obj1, Double[] obj2) {

        double value = 0;

        for (int i = 0; i < obj1.length; i++) {
            double mean = (obj1[i] + obj2[i]) / 2;

            value += (obj1[i] * Math.log(obj1[i] / mean)) + (obj2[i] * Math.log(obj2[i]/mean));
        }
        return value;
    }

}
