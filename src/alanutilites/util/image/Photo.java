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
 */
package alanutilites.util.image;

import alanutilites.util.FileManager;
import alanutilites.util.SystemUtil;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Photo {
        public static final String[] PHOTO_EXTENSIONS = new String[]{"png","jpeg","jpg","jpe","jfif","gif","tif","tiff"};
        private String title;
        private ImageIcon icon;
        private ImageIcon resizedImage;
        private int width;
        private int height;
        private Dimension resizingDimensions;
        
        public Photo(String title, ImageIcon icon){
            this.title = title;
            this.icon = toCompatibleImage(icon);
            resizingDimensions = new Dimension(100,100);
            refresh();
        }
        
        private void refresh(){
            width = icon.getIconWidth();
            height = icon.getIconHeight();
            if(icon.getIconWidth() > resizingDimensions.width || icon.getIconHeight() > resizingDimensions.height){
                resizedImage = resize(resizingDimensions.width,resizingDimensions.height);
            }
            else{
                resizedImage = icon;
            }
        }
        
        private static ImageIcon toCompatibleImage(ImageIcon icon) {
            BufferedImage image = ImageUtil.toBufferedImage(icon);
            GraphicsConfiguration gfx_config = GraphicsEnvironment.
                    getLocalGraphicsEnvironment().getDefaultScreenDevice().
                    getDefaultConfiguration();

            if (image.getColorModel().equals(gfx_config.getColorModel())) {
                return icon;
            }

            BufferedImage new_image = gfx_config.createCompatibleImage(
                    image.getWidth(), image.getHeight(), image.getTransparency());

            Graphics2D g2d = (Graphics2D) new_image.getGraphics();

            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            return ImageUtil.toImageIcon(new_image);
        }
        
        public ImageIcon resize(int newWidth, int newHeight){
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();
            int maxSide = Math.max(width, height);
            int nextWidth = (int)((double)width/((double)maxSide/newWidth));
            int nextHeight = (int)((double)height/((double)maxSide/newHeight));
            ImageIcon icon2 = ImageUtil.scaleImage(icon, nextWidth, nextHeight);
            return icon2;
        }

        public Dimension getResizingDimensions() {
            return resizingDimensions;
        }

        public void setResizingDimensions(Dimension resizingDimensions) {
            this.resizingDimensions = resizingDimensions;
            refresh();
        }
        
        public ImageIcon getResizedImage() {
            return resizedImage;
        }

        public void setResizedImage(ImageIcon resizedImage) {
            this.resizedImage = resizedImage;
        }

        public ImageIcon getIcon() {
            return icon;
        }

        public void setIcon(ImageIcon icon) {
            if(this.icon == icon){
                
            }
            else{
                this.icon = icon;
                refresh();
            }
        }
        
        public boolean export(File file){
            if(file.isDirectory()){
                try {
                    String fileName = file.getAbsolutePath()+SystemUtil.FILE_SEPARATOR+FileManager.getName(file.getAbsolutePath(), title, " ", "png");
//                    System.out.println(fileName);
                    FileManager.saveImage(icon, new File(fileName), "png");
                    return true;
                } catch (IOException ex) {
                    Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return false;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        
        public String[][] getProperties(){
            String[][] properties = new String[][]{
                new String[]{"Title", title},
                new String[]{"Dimensions",  width+"x"+height},
                new String[]{"Width", width+""},
                new String[]{"Height", height+""}
            };
            return properties;
        }
    }