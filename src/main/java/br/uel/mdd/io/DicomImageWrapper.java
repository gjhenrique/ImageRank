package br.uel.mdd.io;

import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.DICOM;

import java.io.InputStream;

/**
 * Example class that opens DICOM files.
 *
 * @author Pedro Tanaka <pedro.stanaka@gmail.com>
 */
public class DicomImageWrapper extends ImageWrapper {

    private static String DEFAULT_NAME = "Padrao";

    public ImagePlus getImage() {
        return image;
    }

    private DICOM image;

    public DicomImageWrapper(String path) {
        Opener opener = new Opener();
        image = (DICOM) opener.openImage(path);
    }

    public DicomImageWrapper(InputStream inputStream, String fileName) {
        image = new DICOM(inputStream);
        image.run(fileName);
    }

    public DicomImageWrapper(InputStream inputStream) {
        this(inputStream, DEFAULT_NAME);
    }

    @Override
    public boolean isColor() {
        return image.getNChannels() > 1;
    }

    @Override
    public int[] getPixelValue(int x, int y) {
        return image.getPixel(x, y);
    }

    @Override
    protected int getHeight() {
        return image.getHeight();
    }

    @Override
    protected int getWidth() {
        return image.getWidth();
    }
}
