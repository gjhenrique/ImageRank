package br.uel.mdd.module;

import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.io.loading.FeatureExtractionLoaderImpl;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class ExtractorModule extends AppModule {

    @Override
    protected void configure() {
        super.configure();

        install(new FactoryModuleBuilder().
                implement(FeatureExtractionLoader.class, FeatureExtractionLoaderImpl.class).
                build(FeatureExtractionLoaderFactory.class));
    }



}
