package br.uel.mdd.metric;

public abstract class MinkowskiDistance implements MetricEvaluator {

    @Override
    public double getDistance(double[] obj1, double[] obj2) {

        if (obj1.length != obj2.length) {
            throw new IllegalArgumentException("Vetor de tamnaho" + obj1.length + " diferente de " + obj2.length);
        }

        double value = 0;
        for (int i = 0; i < obj1.length; i++) {
            double absDiff = Math.abs(obj1[i] - obj2[i]);
            value += Math.pow(absDiff, order());
        }

//        Só funciona com valores positivos
        return Math.pow(value, 1.0 / order());
    }

    public abstract int order();
}
