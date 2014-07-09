package br.uel.mdd.db.tables.pojos;

/**
 * @author pedro
 * @TODO Auto-generated comment
 * <p/>
 */
public final class PrecisionRecall implements java.io.Serializable {

        private Integer extractorId;
        private Double precision;
        private Double recall;

        public PrecisionRecall() {}

        public PrecisionRecall(
                Integer  extractorId,
                Double   precision,
                Double   recall
        ) {
            this.extractorId = extractorId;
            this.precision = precision;
            this.recall = recall;
        }


    public Integer getExtractorId() {
        return extractorId;
    }

    public void setExtractorId(Integer extractorId) {
        this.extractorId = extractorId;
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
                "extractorId=" + extractorId +
                ", recall=" + recall +
                ", precision=" + precision +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().isInstance(PrecisionRecall.class)){
            PrecisionRecall pr = (PrecisionRecall) obj;
            return pr.getExtractorId().equals(this.extractorId);
        }
        return super.equals(obj);
    }
}
