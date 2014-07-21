package br.uel.mdd.extractor;

public class FeatureNotImplemented extends RuntimeException {

    private FeatureExtractor extractor;

    public FeatureNotImplemented(FeatureExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public String getMessage() {
        return extractor.toString() + " Not Implemented";
    }
}
