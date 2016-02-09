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
package alanutilites.util.popup_window;

import alanutilites.util.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.Serializable;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class ToolTip extends PopupWindow implements Serializable{
    private static final long serialVersionUID = 1337570192837469l;
    private static final Font DEFAULT_FONT = new Font("Arial",1,12);
    private int singleLineHeight;
    private String[] messages;
    private String message;
    private int length;
    private int lineGap;
    
    private Font font;
    
    private Color foregroundColor;
    
    public ToolTip(String message,Font font,int lineGap){
        super(0,0,0,0);
        this.message = message;
        this.lineGap = lineGap;
        foregroundColor = Color.BLACK;
        if(font != null){
            this.font = font;
        }
        else{
            this.font = DEFAULT_FONT;
        }
        messages = this.message.split("\n");
        length = Text.getLargestNumber(messages, this.font);
        setWidth(length);
        singleLineHeight = Text.getHeightOfString(this.font);
        setHeight(Text.getHeightOfString(this.font)*messages.length);  
    }
    
    
    @Override
    public void paintMethod(Graphics2D gd){
        if(getX()+getWidth()+20 >= Toolkit.getDefaultToolkit().getScreenSize().width){
            setX(getX()-getWidth());
        }
        int tempX = 0;
        int tempY = 0;
        gd.setColor(foregroundColor);
        tempY+=singleLineHeight/2+2;
        gd.setFont(font);
        for(int i=0;i<messages.length;i++){
            gd.drawString(messages[i],tempX,tempY+lineGap);
            tempY+=singleLineHeight;
        }  
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessages(String message) {
        this.message = message;
        messages = this.message.split("\n");
        updateFont();
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;    
        updateFont();
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
    
    public void updateFont(){
        length = Text.getLargestNumber(messages, font);
        setWidth(length);
        singleLineHeight = Text.getHeightOfString(font);
        setHeight(Text.getHeightOfString(font)*messages.length);
    }
    
    
}
