package br.uel.io;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Example class that opens DICOM files.
 *
 * @author Pedro Tanaka <pedro.stanaka@gmail.com>
 */
public class DicomFileOpener {

    public ImagePlus getImage() {
        return image;
    }

    private ImagePlus image;

    public DicomFileOpener(String filePath) {
        image = (ImagePlus) IJ.runPlugIn("ij.plugin.DICOM", filePath);
        if(image == null)
            throw new RuntimeException("Imagem " + filePath + " não existe");
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

        for (int i = 0;  i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                processor.putPixelValue(i, j, pixels[i][j]);
            }
        }

        return newImage;
    }

}
