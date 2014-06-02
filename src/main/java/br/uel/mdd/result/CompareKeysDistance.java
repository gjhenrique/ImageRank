package br.uel.mdd.result;

import java.util.Comparator;

public class CompareKeysDistance implements Comparator<ResultPair> {

    @Override
    public int compare(ResultPair o1, ResultPair o2) {

        if (o1.getDistance() == o2.getDistance())
            return -1;

        return o1.getDistance() < o2.getDistance() ? -1
                : o1.getDistance() > o2.getDistance() ? 1
                : 0;
    }
}

