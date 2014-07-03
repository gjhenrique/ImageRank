package br.uel.mdd.module;

import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.io.loading.FeatureExtractionLoader;

public interface FeatureExtractionLoaderFactory {

    public FeatureExtractionLoader create(Extractors extractor);
}
