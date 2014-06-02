package br.uel.mdd.result;

import java.util.*;

public class TreeResult<T> {

    private TreeMap<ResultPair<T>, ResultPair<T>> pairs = new TreeMap<ResultPair<T>, ResultPair<T>>(
            new CompareKeysDistance());

    private Double[] sample;

    private int k;

    private boolean tie;

    public TreeResult(Double[] sample, int k, boolean tie) {
        this.sample = sample;
        this.k = k;
        this.tie = tie;
    }

    public TreeResult(Double[] sample, int k) {
        this(sample, k, false);
    }

    public ResultPair getPair(int idx) {
        int i = 0;
        for (Map.Entry<ResultPair<T>, ResultPair<T>> entry : pairs.entrySet()) {
            if (i == idx) {
                return entry.getValue();
            }
            i++;
        }
        return null;
    }

    public void addPair(T object, double distance) {
        ResultPair<T> result = new ResultPair<T>(object, distance);

        this.addPair(result);
    }

    public void addPair(ResultPair resultPair) {
        pairs.put(resultPair, resultPair);
    }

    public double knnCutOldResult() {
        if (this.getNumberOfEntries() >= k) {
            this.cut(k);
        }
        return this.getMaximumDistance();
    }

    public Double[] getSample() {
        return this.sample;
    }

    public Collection<ResultPair> getPairs() {
        return new ArrayList<ResultPair>(pairs.values());
    }

    public int getNumberOfEntries() {
        return this.pairs.size();
    }

    public void cut(int limit) {

        int difference = this.pairs.size() - limit;

        for (int i = 0; i < difference; i++) {
            this.pairs.pollLastEntry();
        }

    }

    public double getMaximumDistance() {
        return this.pairs.lastKey().getDistance();
    }

    @Override
    public String toString() {

        String s = "Resultado ";
        for (Map.Entry<ResultPair<T>, ResultPair<T>> entry : pairs.entrySet()) {
            ResultPair<T> pair = entry.getValue();

            T element = pair.getObject();

            String strReturn = "\n";
            strReturn += "\n" + element;

            s += strReturn + "\t" + pair.getDistance();
        }
        return s;
    }

}
