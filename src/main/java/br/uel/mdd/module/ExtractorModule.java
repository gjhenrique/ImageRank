package br.uel.mdd.module;

import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.extractor.ReducedScaleWaveletExtractor;
import com.google.inject.name.Names;
import math.jwave.transforms.wavelets.Wavelet;

public class ExtractorModule extends AppModule {

    private Extractors extractors;

    public ExtractorModule(Extractors extractors) {
        this.extractors = extractors;
    }

    @Override
    protected void configure() {
        super.configure();

        Class clazzFeatureExtractor = this.findClassByName(getExtractorsPackage(), extractors.getClassName());
        bind(FeatureExtractor.class).to(clazzFeatureExtractor);

        if (clazzFeatureExtractor == ReducedScaleWaveletExtractor.class) {
            bind(Integer.class).annotatedWith(Names.named("levels")).toInstance(extractors.getLevelsWavelet());

            Class clazzFilter = this.findClassByName(getFilterPackage(), extractors.getFilterIdentifier());
            bind(Wavelet.class).annotatedWith(Names.named("filter")).to(clazzFilter);
        }

        bind(Extractors.class).toInstance(extractors);
    }

    private String getExtractorsPackage() {
        return "br.uel.mdd.extractor";
    }


    public String getFilterPackage() {
        String filterIdentifier = extractors.getFilterIdentifier();

        String packageName = "math.jwave.transforms.wavelets";

//        The wavelet Haar is already in the root of the package
        if (filterIdentifier.startsWith("Coiflet"))
            packageName += ".coiflet";
        else if (filterIdentifier.startsWith("Symlet"))
            packageName += ".symlets";
        else if (filterIdentifier.startsWith("Daubechies"))
            packageName += ".daubechies";

        return packageName;
    }

    private Class findClassByName(String packageName, String className) {
        Class clazzFeatureExtractor = null;
        try {
            clazzFeatureExtractor = Class.forName(packageName + "." + className);
        } catch (ClassNotFoundException e) {
        }
        return clazzFeatureExtractor;
    }


}
