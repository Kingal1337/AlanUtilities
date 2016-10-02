/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alan Tsui
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package alanutilites.util.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class ImageUtil {
    
    /**
     * Do not let anyone instantiate this class.
     */
    private ImageUtil(){}
    
    /**
     * Converts ImageIcon to an Image
     * @param image  an ImageIcon that will be converted
     * @return  returns an Image
     */
    public static Image toImage(ImageIcon image){
        return image.getImage();
    }
    
    /**
     * Converts BufferedImage to an Image
     * @param image  an BufferedImage that will be converted
     * @return  returns an Image
     */
    public static Image toImage(BufferedImage image){
        return (Image)image;
    }
    
    /**
     * Converts Image to an ImageIcon
     * @param image  an Image that will be converted
     * @return  returns an ImageIcon
     */
    public static ImageIcon toImageIcon(Image image){
        return new ImageIcon(image);
    }    
    
    /**
     * Converts BufferedImage to an ImageIcon
     * @param image  an BufferedImage that will be converted
     * @return  returns an ImageIcon
     */
    public static ImageIcon toImageIcon(BufferedImage image){
        return new ImageIcon(image);
    }
        
    /**
     * Converts Image to an BufferedImage
     * @param image  an Image that will be converted
     * @return  returns an BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image){
        return toBufferedImage(toImageIcon(image));
    }
    
    /**
     * Converts ImageIcon to an BufferedImage
     * @param image  an ImageIcon that will be converted
     * @return  returns an BufferedImage
     */        
    public static BufferedImage toBufferedImage(ImageIcon image) {
        Image img = image.getImage();
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TRANSLUCENT);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
    
    /**
     * Rotates an Image a certain degree
     * @param image  the Image that needs to be rotated
     * @param angle  the amount of degrees that needs to be rotated
     * @return  returns a rotated Image
     */
    public static Image rotateImage(Image image, int angle){
        BufferedImage bufferedImage = toBufferedImage(image);
        return toImage(rotateImage(bufferedImage, angle));
    }
        
    /**
     * Rotates an BufferedImage a certain degree
     * @param image  the BufferedImage that needs to be rotated
     * @param angle  the amount of degrees that needs to be rotated
     * @return  returns a rotated BufferedImage
     */
    public static BufferedImage rotateImage(BufferedImage image, int angle) {
        double radians;
        if(angle < 0 || angle > 360){
            return null;
        }
        else{
            radians = Math.toRadians(angle);
        }
        double sin = Math.abs(Math.sin(radians)), cos = Math.abs(Math.cos(radians));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D gd = result.createGraphics();
        gd.translate((neww-w)/2, (newh-h)/2);
        gd.rotate(radians, w/2, h/2);
        gd.drawRenderedImage(image, null);
        gd.dispose();
        return result;
    }
        
    /**
     * Rotates an ImageIcon a certain degree
     * @param image  the ImageIcon that needs to be rotated
     * @param angle  the amount of degrees that needs to be rotated
     * @return  returns a rotated ImageIcon
     */
    public static ImageIcon rotateImage(ImageIcon image, int angle) {
        BufferedImage buff = toBufferedImage(image);
        return toImageIcon(rotateImage(buff, angle));
    }
    
    /**
     * Flips an image horizontally
     * @param image  the image that needs to be flipped
     * @return  return a flipped image
     */
    public static BufferedImage flipHorizontally(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage newImg = new BufferedImage(w, h, image.getType());
        Graphics2D g = newImg.createGraphics();
        g.drawImage(image, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return newImg;
    }
    
    /**
     * Flips an image horizontally
     * @param image  the image that needs to be flipped
     * @return  return a flipped image
     */
    public static Image flipHorizontally(Image image) {
        return toImage(flipHorizontally(toBufferedImage(image)));
    }
    
    /**
     * Flips an image horizontally
     * @param image  the image that needs to be flipped
     * @return  return a flipped image
     */
    public static ImageIcon flipHorizontally(ImageIcon image) {
        return toImageIcon(flipHorizontally(toBufferedImage(image)));
    }
    
    /**
     * Flips an image vertically
     * @param image  the image that needs to be flipped
     * @return  return a flipped image
     */
    public static BufferedImage flipVertically(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage newImage = new BufferedImage(w, h, image.getColorModel().getTransparency());
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return newImage;
    }
    
    /**
     * Flips an image vertically
     * @param image  the image that needs to be flipped
     * @return  return a flipped image
     */
    public static Image flipVertically(Image image) {
        return toImage(flipVertically(toBufferedImage(image)));
    }
    
    /**
     * Flips an image vertically
     * @param image  the image that needs to be flipped
     * @return  return a flipped image
     */
    public static ImageIcon flipVertically(ImageIcon image) {
        return toImageIcon(flipVertically(toBufferedImage(image)));
    }
    
    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
    
    /**
     * Resizes a BufferedImage
     * @param originalImage  the BufferedImage
     * @param scaledWidth  the new image width
     * @param scaledHeight  the new image height
     * @return  returns a resized BufferedImage
     */
    public static BufferedImage resizeImage(BufferedImage originalImage,
            int scaledWidth, int scaledHeight) {
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, Transparency.TRANSLUCENT);
        Graphics2D g = scaledBI.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }
    
    /**
     * Scales an image down without losing quality
     * @param originalImage  the image that needs to be resized
     * @param scaledWidth  the new width
     * @param scaledHeight  the new height
     * @return  returns a modified image;
     */
    public static BufferedImage scaleImage(BufferedImage originalImage,
            int scaledWidth, int scaledHeight) {
        if((scaledWidth > originalImage.getWidth()) || (scaledHeight > originalImage.getHeight())){
            return resizeImage(originalImage, scaledWidth, scaledHeight);
        }
        int type = (originalImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage newImage = originalImage;
        BufferedImage scratchImage = null;
        Graphics2D gd = null;

        int w = originalImage.getWidth();
        int h = originalImage.getHeight();

        int prevW = w;
        int prevH = h;
        
        do {
            if (w > scaledWidth) {
                w /= 2;
                w = (w < scaledWidth) ? scaledWidth : w;
            }

            if (h > scaledHeight) {
                h /= 2;
                h = (h < scaledHeight) ? scaledHeight : h;
            }

            if (scratchImage == null) {
                scratchImage = new BufferedImage(w, h, type);
                gd = scratchImage.createGraphics();
            }

            gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gd.drawImage(newImage, 0, 0, w, h, 0, 0, prevW, prevH, null);

            prevW = w;
            prevH = h;
            newImage = scratchImage;
        } while (w != scaledWidth || h != scaledHeight);

        if (gd != null) {
            gd.dispose();
        }

        if (scaledWidth != newImage.getWidth() || scaledHeight != newImage.getHeight()) {
            scratchImage = new BufferedImage(scaledWidth, scaledHeight, type);
            gd = scratchImage.createGraphics();
            gd.drawImage(newImage, 0, 0, null);
            gd.dispose();
            newImage = scratchImage;
        }

        return newImage;
    }
    
    /**
     * Scales an image down without losing quality
     * @param originalImage  the image that needs to be resized
     * @param scaledWidth  the new width
     * @param scaledHeight  the new height
     * @return  returns a modified image;
     */
    public static ImageIcon scaleImage(ImageIcon originalImage,
            int scaledWidth, int scaledHeight) {
        return toImageIcon(scaleImage(toBufferedImage(originalImage),scaledWidth, scaledHeight));
    }
    
    /**
     * Scales an image down without losing quality
     * @param originalImage  the image that needs to be resized
     * @param scaledWidth  the new width
     * @param scaledHeight  the new height
     * @return  returns a modified image;
     */
    public static Image scaleImage(Image originalImage,
            int scaledWidth, int scaledHeight) {
        return toImage(scaleImage(toBufferedImage(originalImage),scaledWidth, scaledHeight));
    }
    
    /**
     * Resizes a ImageIcon
     * @param originalImage  the ImageIcon
     * @param scaledWidth  the new image width
     * @param scaledHeight  the new image height
     * @return  returns a resized ImageIcon
     */
    public static ImageIcon resizeImage(ImageIcon originalImage,
            int scaledWidth, int scaledHeight) {
        return toImageIcon(resizeImage(toBufferedImage(originalImage),scaledWidth, scaledHeight));
    }
    
    /**
     * Resizes a Image
     * @param originalImage  the Image
     * @param scaledWidth  the new image width
     * @param scaledHeight  the new image height
     * @return  returns a resized Image
     */
    public static Image resizeImage(Image originalImage,
            int scaledWidth, int scaledHeight) {
        return toImage(resizeImage(toBufferedImage(originalImage),scaledWidth, scaledHeight));
    }
    
    /**
     * Changes a certain color to another color
     * @param image  the image
     * @param color  color to look for
     * @param toColor  color to change to
     * @return  returns a new modified image
     */
    public static Image changeColor(Image image, final Color color, final Color toColor) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return toColor.getRGB();
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
    
    /**
     * Gets all the frames from a GIF file
     * @param gifImage  the GIF image
     * @return  returns all the GIF images
     */
    public static ImageIcon[] getGIFFrames(File gifImage){
        try {
            ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
            ImageInputStream in = ImageIO.createImageInputStream(gifImage);
            reader.setInput(in);
            int count = reader.getNumImages(true);
            ImageIcon[] images = new ImageIcon[count];
            for (int i = 0; i < images.length; i++)
            {
                images[i] = toImageIcon(reader.read(i));
            }
            return images;
        } catch (IOException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
   /**
    * Crops an image in a certain bounds
    * @param image  the image that needs to be cropped
    * @param x  the x of the bounds that need to be cropped
    * @param y  the y of the bounds that need to be cropped
    * @param width  the width of the bounds
    * @param height  the height of the bounds
    * @return  returns a cropped image
    */
    public static Image crop(Image image, int x, int y, int width, int height){
        Image newImage = Toolkit.getDefaultToolkit().
                createImage(new FilteredImageSource(image.getSource(),
            new CropImageFilter(x, y, width, height)));
        
        return newImage;
    }
    
   /**
    * Crops an image in a certain bounds
    * @param icon  the image that needs to be cropped
    * @param x  the x of the bounds that need to be cropped
    * @param y  the y of the bounds that need to be cropped
    * @param width  the width of the bounds
    * @param height  the height of the bounds
    * @return  returns a cropped image
    */
    public static ImageIcon crop(ImageIcon icon, int x, int y, int width, int height){
        Image image = Toolkit.getDefaultToolkit().
                createImage(new FilteredImageSource(icon.getImage().getSource(),
            new CropImageFilter(x, y, width, height)));
        
        return toImageIcon(image);
    }
    
   /**
    * Crops an image in a certain bounds
    * @param image  the image that needs to be cropped
    * @param x  the x of the bounds that need to be cropped
    * @param y  the y of the bounds that need to be cropped
    * @param width  the width of the bounds
    * @param height  the height of the bounds
    * @return  returns a cropped image
    */
    public static BufferedImage crop(BufferedImage image, int x, int y, int width, int height){
        return image.getSubimage(x, y, width, height);
    }
    
   /**
    * Crops an image in a certain bounds
    * @param image  the image that needs to be cropped
    * @param rectangle  the bounds that need to be cropped
    * @return  returns a cropped image
    */
    public static Image crop(Image image, Rectangle rectangle){
        return crop(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
   /**
    * Crops an image in a certain bounds
    * @param icon  the image that needs to be cropped
    * @param rectangle  the bounds that need to be cropped
    * @return  returns a cropped image
    */
    public static ImageIcon crop(ImageIcon icon, Rectangle rectangle){
        return crop(icon, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
   /**
    * Crops an image in a certain bounds
    * @param image  the image that needs to be cropped
    * @param rectangle  the bounds that need to be cropped
    * @return  returns a cropped image
    */
    public static BufferedImage crop(BufferedImage image, Rectangle rectangle){
        return crop(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
}
