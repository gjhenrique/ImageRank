package br.uel.mdd.io.loading;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.io.ImageWrapper;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

public class FeatureExtractionLoaderImpl implements FeatureExtractionLoader {

    private FeatureExtractor featureExtractor;

    private Extractors extractor;

    private ExtractionsDao extractionsDao;

    private final Logger logger = LoggerFactory.getLogger(FeatureExtractionLoaderImpl.class);

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
        logger.info("Extracting feature of image {} with extractor {}", image.getFileName(), featureExtractor);

        Extractions extractions = extractionsDao.fetchImageIdAndExtractorId(image.getId(), extractor.getId());

        if (extractions == null) {

            ImageWrapper wrapper = this.getImageWrapper(image);

            long start = System.nanoTime();
            double[] features = featureExtractor.extractFeature(wrapper);

            logger.debug("Extract feature {}", Arrays.toString(features));

            long elapsedTime = System.nanoTime() - start;

            Double[] featuresContainer = castPrimitiveToContainer(features);

            extractions = this.buildExtraction(featuresContainer, image, elapsedTime);

            logger.debug("Inserting extraction {} in the database", extractions.getId());

            extractionsDao.insertNullPk(extractions);
        }
        else {
            logger.info("Extraction with Image {} and Extractor {} already exists in the database", image.getId(), extractor.getId());
        }
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
