package br.uel.mdd.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Pedro Tanaka
 *
 * Image wrapper for commom image formats supported by Java 2D Graphics.<br/>
 * These formats are: <br/>
 *  <ul>
 *      <li>JPG</li>
 *      <li>PNG</li>
 *      <li>GIF</li>
 *  </ul>
 *
 */
public class CommonImageWrapper extends ImageWrapper{

    private BufferedImage image;

    public CommonImageWrapper(InputStream stream) {
        try {
            this.image = ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean supportColor() {
        return image.getColorModel().getNumComponents() > 1;
    }

    @Override
    protected boolean supportAlpha() {
        return image.getColorModel().hasAlpha();
    }

    /**
     * Returns the pixel value at (x,y) as a 4 element array. Grayscale values
     * are returned in the first element. RGB values are returned in the first
     * 3 elements, if it supports alpha the last index represents it.
     * @param x Index x of image
     * @param y Index y of image
     */
    @Override
    protected int[] getPixelValue(int x, int y) {
        if (supportAlpha()){
            return getPixelARGB(x, y);
        }else if(supportColor()){
            return getPixelRGB(x,y);
        }else{
            return new int[]{image.getRGB(x,y), 0, 0, 0};
        }
    }

    private int[] getPixelRGB(int x, int y) {
        int pixel = image.getRGB(x,y);
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue, 0};
    }

    @Override
    protected int getHeight() {
        return image.getHeight();
    }

    @Override
    protected int getWidth() {
        return image.getWidth();
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    /**
     * The first three positions are the RBG and the last position is the alpha channel
     * @param x Index x of image
     * @param y Index y of image
     * @return
     */
    private int[] getPixelARGB(int x, int y){
        int pixel = image.getRGB(x,y);
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue, alpha};
    }
}
