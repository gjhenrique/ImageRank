package br.uel.mdd.io;

import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.DICOM;
import ij.process.ImageProcessor;

import java.io.InputStream;

/**
 * Example class that opens DICOM files.
 *
 * @author Pedro Tanaka <pedro.stanaka@gmail.com>
 */
public class DicomFileOpener {

    private static String DEFAULT_NAME = "Padrao";

    public ImagePlus getImage() {
        return image;
    }

    private DICOM image;

    public DicomFileOpener(String path) {
        Opener opener = new Opener();
        image = (DICOM) opener.openImage(path);
    }

    public DicomFileOpener(InputStream inputStream, String fileName) {
        image = new DICOM(inputStream);
        image.run(fileName);
    }

    public DicomFileOpener(InputStream inputStream) {
        this(inputStream, DEFAULT_NAME);
    }

    public double[][] getGrayPixelMatrix() {

        double[][] matrix = new double[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                matrix[i][j] = (double) image.getPixel(i, j)[0];
            }
        }
        return matrix;
    }

    public ImagePlus createNewImage(double[][] pixels) {

        ImagePlus newImage = image.duplicate();
        ImageProcessor processor = newImage.getProcessor();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                processor.putPixelValue(i, j, pixels[i][j]);
            }
        }

        return newImage;
    }

}
