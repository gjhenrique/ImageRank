package br.uel.mdd.extractor;

import br.uel.mdd.io.ImageWrapper;
import de.lmu.ifi.dbs.jfeaturelib.features.AbstractFeatureDescriptor;
import ij.ImagePlus;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.List;

public class JFeatureLib implements FeatureExtractor {

    @Inject
    private AbstractFeatureDescriptor abstractFeatureDescriptor;

    @Override
    public double[] extractFeature(ImageWrapper imageWrapper) {
        BufferedImage bufferedImage = imageWrapper.getImage();
        ImagePlus imagePlus = new ImagePlus("TITLE", bufferedImage);

        // Avoid getting the same results for different images
        abstractFeatureDescriptor.getFeatures().clear();
        abstractFeatureDescriptor.run(imagePlus.getProcessor());

        List<double[]> features = abstractFeatureDescriptor.getFeatures();

        return features.get(0);
    }
}
