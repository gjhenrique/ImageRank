package br.uel.mdd.io.loading;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.io.ImageWrapper;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.List;

public class FeatureExtractionLoaderImpl implements FeatureExtractionLoader {

    private FeatureExtractor featureExtractor;

    private Extractors extractor;

    private ExtractionsDao extractionsDao;

    @Inject
    public FeatureExtractionLoaderImpl(@Assisted FeatureExtractor featureExtractor,
                                       @Assisted Extractors extractor,
                                       ExtractionsDao extractionsDao) {
        this.featureExtractor = featureExtractor;
        this.extractor = extractor;
        this.extractionsDao = extractionsDao;
    }

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

    private ImageWrapper getImageWrapper(Images image) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getImage());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);

        return ImageWrapper.createImageOpener(bufferedInputStream, image.getMimeType());
    }

    private Double[] castPrimitiveToContainer(double[] features) {
        Double[] featuresContainer = new Double[features.length];

        for (int i = 0; i < features.length; i++) {
            featuresContainer[i] = features[i];
        }

        return featuresContainer;
    }

    private Extractions buildExtraction(Double[] features, Images image, long elapsedTime) {
        Extractions extractions = new Extractions();

        extractions.setExtractionData(features);
        extractions.setExtractorId(extractor.getId());
        extractions.setImageId(image.getId());
        extractions.setExtractionTime(elapsedTime);

        return extractions;
    }
}
