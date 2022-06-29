package br.uel.mdd.utils;

import junit.framework.TestCase;

import java.io.File;

public class MimeTest extends TestCase {

    public void testGetMimeType() throws Exception {
        String path = this.getClass().getResource("/imgs/Dogs/Chihuahua_n02085620_199.jpg").getFile();
        File file = new File(path);
        String mimeType = Mime.getMimeType(file);
        assertEquals(mimeType, "image/jpeg");
    }
}
