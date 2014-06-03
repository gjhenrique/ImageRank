package br.uel.mdd.extractor;

import br.uel.mdd.io.ImageWrapper;

public interface FeatureExtractor {

 public double[] extractFeature(ImageWrapper image);

}