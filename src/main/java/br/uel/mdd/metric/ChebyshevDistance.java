package br.uel.mdd.metric;

public class ChebyshevDistance implements MetricEvaluator {
    @Override
    public double getDistance(Double[] obj1, Double[] obj2) {
        double max = 0;

        for(int i = 0; i < obj1.length; i++) {
            max = Math.max(max, Math.abs(obj1[i] - obj2[i]));
        }

        return max;
    }

    @Override
    public String toString() {
        return "Linf";
    }
}
