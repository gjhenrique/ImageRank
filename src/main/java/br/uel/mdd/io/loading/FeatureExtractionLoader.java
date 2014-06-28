package br.uel.mdd.io.loading;

import br.uel.mdd.db.tables.pojos.Images;

import java.util.List;

public interface FeatureExtractionLoader {

    public void extractFeatures(List<Images> images);

    public void extractFeatures(Images image);
}
