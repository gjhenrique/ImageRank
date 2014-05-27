package br.uel.mdd.metric;

public class ManhattanDistance extends MinkowskiDistance {

    @Override
    public int order() {
        return 1;
    }
}
