package br.uel.mdd.extractor;

import br.uel.mdd.io.ImageWrapper;

public class PerceptualParameter implements FeatureExtractor {

    private String description;

    public PerceptualParameter(String description) {
        this.description = description;
    }

    //    This extractor is not implemented
//    The extractions from the lung datasets are in the file ponciano-extractions.backup
    @Override
    public double[] extractFeature(ImageWrapper image) {
        throw new FeatureNotImplemented(this);
    }

    @Override
    public String toString() {
        return description;
    }
}
