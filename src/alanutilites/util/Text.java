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
package alanutilites.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Text {
    
    
    /**
     * Do not let anyone instantiate this class.
     */
    private Text(){}
    
    public static String[] prettyStringWrap(String originalString, int width, Graphics2D gd){
        Font font = gd.getFont();
        ArrayList<String> strings = new ArrayList<>();
        if(getLengthOfString(originalString,font) > width){
            String[] words = originalString.split(" ");
            String nextString = "";
            for(int i=0;i<words.length;){
                String currentWord = words[i]+" ";
                if(getLengthOfString(currentWord,font) > width){
                    if(!nextString.isEmpty()){
                        strings.add(nextString);
                        nextString = "";
                    }
                    String[] newString = stringWrap(currentWord,width,gd);
                    for(int j=0;j<newString.length;j++){
                        strings.add(newString[j]);
                    }
                    i++;
                }
                else if(getLengthOfString(nextString+currentWord,font) > width){
                    strings.add(nextString);
                    nextString = "";
                }
                else{
                    nextString+=currentWord;
                    i++;
                }
            }
            strings.add(nextString);
        }
        else{
            return new String[]{originalString};
        }
        return strings.toArray(new String[strings.size()]);
    }
    
    /**
     * 
     * @param originalString  the string that is going to be used
     * @param width  the width of the text area
     * @param gd  the Graphics2D
     * @return  returns a array of strings that fits the width
     */
    public static String[] stringWrap(String originalString,int width,Graphics2D gd){
//        String formattedString = "";
        Font font = gd.getFont();
        String originalTempString = originalString;
        String tempString = "";
        int lengthOfString = originalString.length();
        int counter = 0;
        ArrayList<String> strings = new ArrayList<>();
        if(getLengthOfString(originalString,font) > width){
            while(true){
//                System.out.println(getLengthOfString(originalTempString, font)+" "+getLengthOfString(tempString,font)+" "+width);
                if(getLengthOfString(tempString,font) <= width){
                    tempString += originalString.charAt(counter);
                }
                if(getLengthOfString(tempString,font) > width){
                    tempString = tempString.substring(0,tempString.length()-1);
                    counter--;
                    strings.add(tempString);
                    tempString = "";
                    originalTempString = originalString.substring(counter);
                }
                if(counter >= lengthOfString-1){
                    break;                    
                }
                counter++;
            }
            strings.add(originalTempString.substring(1).trim());
        }
        else{
            return new String[]{originalString};
        }
        return strings.toArray(new String[strings.size()]);
    }
    
    public static int getHeightOfStrings(String[] strings, Font font){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        Graphics2D gd = (Graphics2D)g;
        gd.setFont(font);
        
        int height = 0;
        for(int i=0;i<strings.length;i++){
            height += getStringBounds(gd,strings[i],0,0).height;
        }
        return height;
    }
    
    public static Rectangle getStringBounds(Graphics2D g2, String str, float x, float y){
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
    }
    
    /**
     * The length of a string in pixels
     * @param string  the string you need check the length of
     * @param font  the font the string is using
     * @return  returns a number base on how many pixels there are
     */
    public static int getLengthOfString(String string, Font font) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(font);
        int width = fm.stringWidth(string);
        return width;
    }
    
    /**
     * The height of the font in pixels
     * @param font  the font the string is using
     * @return  returns the height of the font
     */
    public static int getHeightOfString(Font font) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(font);
        int height = fm.getMaxAscent();
        return height;
    }
    
    /**
     * Gets the largest string in the string array in pixels
     * @param strings  all the strings that need to be compared
     * @param font  the font all the strings are using
     * @return  returns the largest strings length;
     */
    public static int getLargestNumber(String[] strings, Font font) {
        if(strings.length == 0){
            return 0;
        }
        int largeNumber = getLengthOfString(strings[0], font);
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings.length; j++) {
                if (i != j) {
                    if (getLengthOfString(strings[i], font) < getLengthOfString(strings[j], font)) {
                        if (getLengthOfString(strings[j], font) > largeNumber) {
                            largeNumber = getLengthOfString(strings[j], font);
                        }
                    }
                }
            }
        }
        return largeNumber;
    }
    
    
    /**
     * Removes the extension to string
     * @param string  The string that need the extension removed
     * @return  if the string has an extension to it
     * then returns the string without the extension 
     * else returns the original string
     */
    public static String removeExtension(String string){
        return string.contains(".") ? string.substring(0,string.lastIndexOf(".")) : string;
    }
    
    /**
     * Draws a string in the middle of the rectangle
     * @param string  the string that needs to be in the center
     * @param x  the x of the rectangle
     * @param y  the y of the rectangle
     * @param width  the width of the rectangle
     * @param height  the height of the rectangle
     * @param gd  the graphics2d from the JComponent
     */
    public static void drawCenteredString(String string,int x,int y, int width, int height, Graphics2D gd) {
        Point point = getStringPoint(string, x, y, width, height, gd);
        gd.drawString(string, point.x, point.y);
    }
    
    /**
     * Draws a string in the middle of the rectangle
     * @param string  the string that needs to be in the center
     * @param x  the x of the rectangle
     * @param y  the y of the rectangle
     * @param width  the width of the rectangle
     * @param height  the height of the rectangle
     * @param gd  the graphics2d from the JComponent
     * @return  returns where the string should be drawn
     */
    public static Point getStringPoint(String string,int x,int y, int width, int height, Graphics2D gd) {
        FontMetrics fm = gd.getFontMetrics();
        int xx = ((width - fm.stringWidth(string)) / 2) + x;
        int yy = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2) + y;
        return new Point(xx,yy);
    } 
    
    public static void outlineText(String string, Color outlineColor, Color stringColor, Graphics2D gd, Point point, int outLineSize){
        gd.setColor(outlineColor);
        gd.drawString(string, shiftWest(point.x, outLineSize), shiftNorth(point.y, outLineSize));
        gd.drawString(string, shiftWest(point.x, outLineSize), shiftSouth(point.y, outLineSize));
        gd.drawString(string, shiftWest(point.x, outLineSize), shiftNorth(point.y, outLineSize));
        gd.drawString(string, shiftWest(point.x, outLineSize), shiftSouth(point.y, outLineSize));
        gd.setColor(stringColor);
        gd.drawString(string, point.x, point.y);
    }
    
    public static void shadowText(String string, Graphics2D gd, Color textColor, Color shadowColor, Point point, int shadowWidth){
        gd.setColor(shadowColor);
        gd.drawString(string, shiftEast(point.x, shadowWidth), shiftSouth(point.y, shadowWidth));
        gd.setColor(textColor);
        gd.drawString(string, point.x, point.y);
    }
    
    //<editor-fold defaultstate="collapsed" desc="3D Text">
    public static void draw3DToBottomLeft(String string, Color sideColor, Color topColor, Graphics2D gd, Point point, int amountToPopOut){
        for (int i = 0; i < amountToPopOut; i++) {
           gd.setColor(topColor);
           gd.drawString(string, shiftWest(point.x, i), shiftNorth(shiftSouth(point.y, i), 1));
           gd.setColor(sideColor);
           gd.drawString(string, shiftEast(shiftWest(point.x, i), 1), shiftSouth(point.y, i));
           }
        gd.setColor(topColor);
        gd.drawString(string, shiftWest(point.x, amountToPopOut), shiftSouth(point.y, amountToPopOut));
    }

    public static void draw3DToTopRight(String string, Color sideColor, Color topColor, Graphics2D gd, Point point, int amountToPopOut){
        for (int i = 0; i < amountToPopOut; i++) {
           gd.setColor(topColor);
           gd.drawString(string, shiftEast(point.x, i), shiftSouth(shiftNorth(point.y, i), 1));
           gd.setColor(sideColor);
           gd.drawString(string, shiftWest(shiftEast(point.x, i), 1), shiftNorth(point.y, i));
           }
        gd.setColor(topColor);
        gd.drawString(string, shiftEast(point.x, amountToPopOut), shiftNorth(point.y, amountToPopOut));
    }

    public static void draw3DToTopLeft(String string, Color sideColor, Color topColor, Graphics2D gd, Point point, int amountToPopOut){
        for (int i = 0; i < amountToPopOut; i++) {
           gd.setColor(topColor);
           gd.drawString(string, shiftWest(point.x, i), shiftSouth(shiftNorth(point.y, i), 1));
           gd.setColor(sideColor);
           gd.drawString(string, shiftEast(shiftWest(point.x, i), 1), shiftNorth(point.y, i));
           }
        gd.setColor(topColor);
        gd.drawString(string, shiftWest(point.x, amountToPopOut), shiftNorth(point.y, amountToPopOut));
    }

    public static void draw3DToBottomRight(String string, Color sideColor, Color topColor, Graphics2D gd, Point point, int amountToPopOut){
        for (int i = 0; i < amountToPopOut; i++) {
           gd.setColor(topColor);
           gd.drawString(string, shiftEast(point.x, i), shiftNorth(shiftSouth(point.y, i), 1));
           gd.setColor(sideColor);
           gd.drawString(string, shiftWest(shiftEast(point.x, i), 1), shiftSouth(point.y, i));
           }
        gd.setColor(topColor);
        gd.drawString(string, shiftEast(point.x, amountToPopOut), shiftSouth(point.y, amountToPopOut));
    }
    //</editor-fold>

    private static int shiftNorth(int p, int distance) {
        return (p - distance);
    }

    private static int shiftSouth(int p, int distance) {
        return (p + distance);
    }

    private static int shiftEast(int p, int distance) {
        return (p + distance);
    }

    private static int shiftWest(int p, int distance) {
        return (p - distance);
    }
    
    public static String[] splitString(String string, int interval){
        int arrayLength = (int) Math.ceil(((string.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = string.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = string.substring(j);

        return result;
    }
}
