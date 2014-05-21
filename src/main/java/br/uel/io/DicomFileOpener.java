package br.uel.io;

import ij.IJ;
import ij.ImagePlus;

/**
 * Example class that opens DICOM files.
 * @author Pedro Tanaka <pedro.stanaka@gmail.com>
 */
public class DicomFileOpener {
    public DicomFileOpener(String filePath){
        ImagePlus imagePlus = null;
        imagePlus = (ImagePlus) IJ.runPlugIn("ij.plugin.DICOM", filePath);
        System.out.println("Dimensions: " + imagePlus.getNDimensions());
    }
}
