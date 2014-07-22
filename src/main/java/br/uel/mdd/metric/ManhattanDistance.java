package br.uel.mdd.metric;

public class ManhattanDistance extends MinkowskiDistance {

    @Override
    public int order() {
        return 1;
    }

    @Override
    public String toString() {
        return "Manhattan";
    }
}
