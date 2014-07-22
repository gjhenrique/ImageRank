package br.uel.mdd.db.tables.pojos;

/**
 * @author pedro
 * @TODO Auto-generated comment
 * <p/>
 */
public final class PrecisionRecall implements java.io.Serializable {

        private Integer id;
        private Double precision;
        private Double recall;

        public PrecisionRecall() {}

        public PrecisionRecall(
                Integer  id,
                Double   precision,
                Double   recall
        ) {
            this.id = id;
            this.precision = precision;
            this.recall = recall;
        }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRecall() {
        return recall;
    }

    public void setRecall(Double recall) {
        this.recall = recall;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", recall=" + recall +
                ", precision=" + precision +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().isInstance(PrecisionRecall.class)){
            PrecisionRecall pr = (PrecisionRecall) obj;
            return pr.getId().equals(this.id);
        }
        return super.equals(obj);
    }
}
