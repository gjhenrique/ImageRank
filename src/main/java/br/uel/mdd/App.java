package br.uel.mdd;

import br.uel.io.DicomFileOpener;
import math.jwave.exceptions.JWaveFailure;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JWaveFailure {
        new App();
    }

    public App(){
        String filePath = this.getClass().getResource("/pulmao_enfisema.dcm").getPath();
        DicomFileOpener dicomOpener = new DicomFileOpener(filePath);

        // Get the gray value of a random pixel
        System.out.println(Arrays.toString(dicomOpener.getImage().getPixel(1, 20)));
    }
}
