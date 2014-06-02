package br.uel.mdd.result;

import java.io.Serializable;
import java.util.Comparator;

public class ResultPair<T> implements Comparator<ResultPair>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 67629311405862738L;

    private T object;

    private double distance;

    public ResultPair(T object, double distance) {
        this.object = object;
        this.distance = distance;
    }

    public T getObject() {
        return object;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int compare(ResultPair o1, ResultPair o2) {

        if (o1.getDistance() == o2.getDistance())
            return -1;

        return o1.getDistance() < o2.getDistance() ? -1
                : o1.getDistance() > o2.getDistance() ? 1
                : 0;
    }
}
