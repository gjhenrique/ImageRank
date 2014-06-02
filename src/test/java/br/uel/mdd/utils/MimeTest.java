package br.uel.mdd.utils;

import junit.framework.TestCase;

import java.io.File;

public class MimeTest extends TestCase {

    public void testGetMimeType() throws Exception {
        String path = this.getClass().getResource("/pulmao_enfisema.dcm").getFile();
        File file = new File(path);
        String mimeType = Mime.getMimeType(file);
        assertEquals(mimeType, "image/jpeg");
    }

}