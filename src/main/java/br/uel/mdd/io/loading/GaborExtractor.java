package br.uel.mdd.io.loading;

import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.io.ImageWrapper;
import de.lmu.ifi.dbs.jfeaturelib.features.Gabor;
import ij.ImagePlus;

import java.awt.image.BufferedImage;
import java.util.List;

public class GaborExtractor implements FeatureExtractor {

    Gabor gabor = new Gabor();

    @Override
    public double[] extractFeature(ImageWrapper imageWrapper) {
        BufferedImage bufferedImage = imageWrapper.getImage();
        ImagePlus imagePlus = new ImagePlus("TITLE", bufferedImage);

        gabor.run(imagePlus.getProcessor());

        List<double[]> features = gabor.getFeatures();

        return features.get(0);
    }
}
