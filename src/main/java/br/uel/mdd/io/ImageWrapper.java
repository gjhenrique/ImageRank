package br.uel.mdd.io;

import java.io.InputStream;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 01/06/14.
 */
public abstract class ImageWrapper {

    public static ImageWrapper createImageOpener(InputStream stream, String mime){
        // TODO: Open different file types, not only DICOM and common formats (jpg, png and bpm)
        if(mime.equals("application/dicom")){
            return new DicomImageWrapper(stream);
        }else{
            // @TODO return java BufferedImage
            return null;
        }
    }

    protected abstract boolean supportColor();

    protected abstract boolean supportAlpha();

    protected abstract int[] getPixelValue(int x, int y);

    protected abstract int getHeight();

    protected abstract int getWidth();

    public double[][] getPixelMatrix(){

        double[][] matrix = new double[getHeight()][getWidth()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                matrix[i][j] = (double) getPixelValue(i, j)[0];
            }
        }
        return matrix;
    }

}
