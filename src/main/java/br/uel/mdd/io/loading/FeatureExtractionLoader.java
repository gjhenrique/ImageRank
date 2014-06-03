package br.uel.mdd.io.loading;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.io.ImageWrapper;

import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.List;

public class FeatureExtractionLoader {

    @Inject
    FeatureExtractor featureExtractor;

    @Inject
    Extractors extractor;

    @Inject
    ExtractionsDao extractionsDao;

    public void extractFeatures(List<Images> images) {
        for (Images image : images) {
            extractFeatures(image);
        }
    }

    public void extractFeatures(Images image) {

        ImageWrapper wrapper = this.getImageWrapper(image);

        long start = System.nanoTime();
        double[] features = featureExtractor.extractFeature(wrapper);
        long elapsedTime = System.nanoTime() - start;

        Double[] featuresContainer = castPrimitiveToContainer(features);

        Extractions extractions = this.buildExtraction(featuresContainer, image, elapsedTime);

        extractionsDao.insertNullPk(extractions);
    }

    public ImageWrapper getImageWrapper (Images image) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getImage());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);

        return ImageWrapper.createImageOpener(bufferedInputStream, image.getMimeType());
    }

    public Double[] castPrimitiveToContainer(double[] features) {
        Double[] featuresContainer = new Double[features.length];

        for (int i = 0; i < features.length; i++) {
            featuresContainer[i] = features[i];
        }

        return featuresContainer;
    }

    public Extractions buildExtraction(Double[] features, Images image, long elapsedTime) {
        Extractions extractions = new Extractions();

        extractions.setExtractionData(features);
        extractions.setExtractorId(extractor.getId());
        extractions.setImageId(image.getId());
        extractions.setExtractionTime(elapsedTime);

        return extractions;
    }
}
