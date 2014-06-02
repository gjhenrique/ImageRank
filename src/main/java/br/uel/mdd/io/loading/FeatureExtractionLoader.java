package br.uel.mdd.io.loading;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.io.ImageWrapper;
import org.apache.tika.Tika;

import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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

        double[][] pixels = this.getImagePixels(image);

        double[] features = featureExtractor.extractFeature(pixels);

        Double[] featuresContainer = castPrimitiveToContainer(features);

        Extractions extractions = this.buildExtraction(featuresContainer, image);

        extractionsDao.insertNullPk(extractions);
    }

    public double[][] getImagePixels(Images image) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getImage());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);

        String mimeType = "";
        try {
            mimeType = new Tika().detect(bufferedInputStream);
        } catch (IOException e) {
            System.out.println("The mime type of the file could not be detected. Please check if the file is valid.");
            e.printStackTrace();
        }
        ImageWrapper opener = ImageWrapper.createImageOpener(bufferedInputStream, mimeType);
        return opener.getPixelMatrix();
    }

    public Double[] castPrimitiveToContainer(double[] features) {
        Double[] featuresContainer = new Double[features.length];

        for (int i = 0; i < features.length; i++) {
            featuresContainer[i] = features[i];
        }

        return featuresContainer;
    }

    public Extractions buildExtraction(Double[] features, Images image) {
        Extractions extractions = new Extractions();

        extractions.setExtractionData(features);
        extractions.setExtractorId(extractor.getId());
        extractions.setImageId(image.getId());

        return extractions;
    }
}
