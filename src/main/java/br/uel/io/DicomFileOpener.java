package br.uel.io;

import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;

/**
 * Example class that opens DICOM files.
 * @author Pedro Tanaka <pedro.stanaka@gmail.com>
 */
public class DicomFileOpener {

    public ImagePlus getImage() {
        return image;
    }

    private ImagePlus image;

    public DicomFileOpener(String filePath){
        image = (ImagePlus) IJ.runPlugIn("ij.plugin.DICOM", filePath);
    }

    public double[][] getGrayPixelMatrix(){
        double[][] matrix = null;
        if(image != null && image.getType() == Opener.DICOM){
            matrix = new double[image.getWidth()][image.getHeight()];
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    matrix[i][j] = (double) image.getPixel(i, j)[0];
                }
            }
        }
        return matrix;
    }
}
