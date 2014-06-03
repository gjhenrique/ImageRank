package br.uel.mdd.module;

import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.extractor.ReducedScaleWaveletExtractor;
import br.uel.mdd.extractor.JFeatureLib;
import br.uel.mdd.utils.ReflectionUtils;
import com.google.inject.name.Names;
import de.lmu.ifi.dbs.jfeaturelib.features.AbstractFeatureDescriptor;
import math.jwave.transforms.wavelets.Wavelet;

public class ExtractorModule extends AppModule {

    private Extractors extractors;

    public ExtractorModule(Extractors extractors) {
        this.extractors = extractors;
    }

    @Override
    protected void configure() {
        super.configure();

        Class clazzFeatureExtractor = ReflectionUtils.findClassByName(getExtractorsPackage(), extractors.getClassName());
        bind(FeatureExtractor.class).to(clazzFeatureExtractor);

        if (clazzFeatureExtractor == ReducedScaleWaveletExtractor.class) {
            bind(Integer.class).annotatedWith(Names.named("levels")).toInstance(extractors.getLevelsWavelet());

            Class clazzFilter = ReflectionUtils.findClassByName(getFilterPackage(), extractors.getTypeIdentifier());
            bind(Wavelet.class).annotatedWith(Names.named("filter")).to(clazzFilter);

        } else if (clazzFeatureExtractor == JFeatureLib.class) {

            Class clazzIdentifier = ReflectionUtils.findClassByName(getJFeatureLibPackage(), extractors.getTypeIdentifier());
            bind(AbstractFeatureDescriptor.class).to(clazzIdentifier);

        }

        bind(Extractors.class).toInstance(extractors);
    }

    private String getExtractorsPackage() {
        return "br.uel.mdd.extractor";
    }


    private String getFilterPackage() {
        String filterIdentifier = extractors.getTypeIdentifier();

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

    private String getJFeatureLibPackage() {
        return "de.lmu.ifi.dbs.jfeaturelib.features";
    }

}
