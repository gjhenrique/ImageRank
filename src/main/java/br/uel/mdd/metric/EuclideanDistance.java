package br.uel.mdd.metric;

public class EuclideanDistance extends MinkowskiDistance {
    @Override
    public int order() {
        return 2;
    }
}
