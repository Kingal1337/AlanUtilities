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
package alanutilites.util.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Alan Tsui
 * @version 1.0
 * @since 1.1
 */
public class RainbowText {
    private static List<Color> colors;
    private static boolean initalized;
    private int currentIndex;
    private String text;
    private String[] strings;
    private Font font;
    private FontMetrics metrics;
    public RainbowText(String text, Font font){
        init();
        this.text = text;
        currentIndex = 0;
        strings = text.split("");
        this.font = font;
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        metrics = img.getGraphics().getFontMetrics(font);
    }
    
    public void render(Graphics2D gd, int x, int y){
        Color color = gd.getColor();
        Font ogFont = gd.getFont();
        gd.setFont(font);
        int index = currentIndex;
        int fontWidth;
        for(int i=0;i<strings.length;i++){
            fontWidth = metrics.stringWidth(strings[i]);
            gd.setColor(colors.get(index));
            gd.drawString(strings[i], x, y);
            x+=fontWidth;
            index+=5;
            index = index >= colors.size() ? 0 : index;
        }
        currentIndex--;
        currentIndex = currentIndex < 0 ? colors.size()-1 : currentIndex;
        gd.setColor(color);
        gd.setFont(ogFont);
    }
    
    private static void init(){
        if(!initalized){
            initalized = true;
            colors = new ArrayList<>();
            for (int r = 0; r < 100; r++) {
                colors.add(new Color(r * 255 / 100, 255, 0));
            }
            for (int g = 100; g > 0; g--) {
                colors.add(new Color(255, g * 255 / 100, 0));
            }
            for (int b = 0; b < 100; b++) {
                colors.add(new Color(255, 0, b * 255 / 100));
            }
            for (int r = 100; r > 0; r--) {
                colors.add(new Color(r * 255 / 100, 0, 255));
            }
            for (int g = 0; g < 100; g++) {
                colors.add(new Color(0, g * 255 / 100, 255));
            }
            for (int b = 100; b > 0; b--) {
                colors.add(new Color(0, 255, b * 255 / 100));
            }
            colors.add(new Color(0, 255, 0));            
            colors = Collections.unmodifiableList(colors);
        }
    }
}
