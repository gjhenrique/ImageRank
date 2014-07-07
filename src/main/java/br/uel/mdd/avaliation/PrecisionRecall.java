package br.uel.mdd.avaliation;

public final class PrecisionRecall {

    private float precision;

    private float recall;

    private Integer id;

    public PrecisionRecall(float precision, float recall, Integer id) {
        this.precision = precision;
        this.recall = recall;
        this.id = id;
    }

    public float getPrecision() {
        return precision;
    }

    public float getRecall() {
        return recall;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PrecisionRecall{" +
                "precision=" + precision +
                ", recall=" + recall +
                ", id=" + id +
                '}';
    }
}
