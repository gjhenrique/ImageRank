package br.uel.mdd;

import br.uel.mdd.extractor.FeatureExtractor;

import javax.inject.Inject;

public class ExtractFeatureDatabase {

    @Inject
    FeatureExtractor extractor;

    public void extractFeatures() {
        throw new RuntimeException("Extractor not Implemented");
    }
}
