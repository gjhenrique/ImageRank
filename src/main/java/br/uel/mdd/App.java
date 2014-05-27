package br.uel.mdd;

import br.uel.io.DicomFileOpener;
import ij.ImagePlus;
import math.jwave.Transform;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.Haar1;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws JWaveFailure {
        new App();
    }

    public App() {

        String filePath = this.getClass().getResource("/pulmao_enfisema.dcm").getPath();
        DicomFileOpener dicomOpener = new DicomFileOpener(filePath);

        double[][] imageMatrix = dicomOpener.getGrayPixelMatrix();

        try {
            Wavelet filter = new Haar1();
            Transform transform = new Transform(new FastWaveletTransform(filter), 2);

            double[][] transformedWavelet = transform.forward(imageMatrix);
            ImagePlus newImage = dicomOpener.createNewImage(transformedWavelet);
            newImage.show();
        } catch (JWaveFailure jWaveFailure) {
            jWaveFailure.printStackTrace();
        }

    }
}
