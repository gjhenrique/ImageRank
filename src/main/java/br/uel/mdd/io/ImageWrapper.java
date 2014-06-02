package br.uel.mdd.io;

import java.io.InputStream;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 01/06/14.
 */
public abstract class ImageWrapper {

    public static ImageWrapper createImageOpener(InputStream stream, String mime) {
        if (mime.equals("application/dicom")) {
            return new DicomImageWrapper(stream);
        } else if (mime.startsWith("image")) {
            return new CommonImageWrapper(stream);
        } else {
            throw new IllegalArgumentException("Mime " + mime + " not recognized");
        }
    }

    protected abstract boolean supportColor();

    protected abstract boolean supportAlpha();

    protected abstract int[] getPixelValue(int x, int y);

    protected abstract int getHeight();

    protected abstract int getWidth();

    public double[][] getPixelMatrix() {

        double[][] matrix = new double[getHeight()][getWidth()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                matrix[i][j] = getUnitPixelValue(i, j);
            }
        }
        return matrix;
    }

    private int getUnitPixelValue(int x, int y) {
        int value = 0;
        if (supportColor()) {
//                    TODO
            value = getPixelValue(x, y)[0];
        } else if (supportAlpha()) {
//                    TODO
            value = getPixelValue(x, y)[0];
        } else {
            value = getPixelValue(x, y)[0];
        }
        return value;
    }

}
