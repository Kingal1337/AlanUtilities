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

import alanutilites.shape.Point;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public abstract class PopupWindow implements Serializable{
    private static final long serialVerisonUID = 1337570192837468l;
    
    private Container parentFrame;
    
    private int x;
    private int y;
    private int width;
    private int height;
    private JDialog frame;
    private final JPanel panel;
    
    private Color backgroundColor;
    private Color borderColor;
    
    private int offsetX;
    private int offsetY;

    public PopupWindow(int x, int y, int width, int height) {
        offsetX = 0;
        offsetY = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.WHITE;
        this.borderColor = Color.BLACK;
        frame = new JDialog();
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D gd = (Graphics2D)g;
                
                paintMethod(gd);                
            }
        };   
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(borderColor));
        panel.setPreferredSize(new Dimension(width,height));
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setBounds(x,y,width,height);
        frame.add(panel);
    }
    
    public void update(Container window, Point mouseXY, boolean show){
        int xx = 0;
        int yy = 0;
        
//        System.out.println("Duh Window "+window);
        if(window != null){
            if(window != parentFrame){
                parentFrame = window;
                frame = new JDialog(frame,true);
                frame.setAlwaysOnTop(true);
                frame.setUndecorated(true);
                frame.setBounds(x,y,width,height);
                frame.add(panel);
            }
        }
        if(mouseXY != null){
            xx = (int) mouseXY.x;
            yy = (int) mouseXY.y;
        }
        if(show){
            setX(xx);
            setY(yy);
            
            panel.repaint();
            if(getX()+getWidth() >= Toolkit.getDefaultToolkit().getScreenSize().width){
                setX(getX()-(getWidth()+(offsetX*2)));
            }
            frame.setBounds(getX()+offsetX,getY()+offsetY,getWidth(),getHeight());
            frame.setVisible(true);
        }
        else{
            frame.setVisible(false);            
        }
    }
    
    private static Frame getFrame(Component c) {
        Component w = c;

        while(!(w instanceof Frame) && (w!=null)) {
            w = w.getParent();
        }
        return (Frame)w;
    }
    
    public void setLayout(LayoutManager layout){
        panel.setLayout(layout);
        panel.repaint();
    }
    
    public void addComponents(JComponent component){
        panel.add(component);
        panel.repaint();
    }
    
    public abstract void paintMethod(Graphics2D gd);

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        updateColors();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        updateColors();
    }
    
    private void updateColors(){        
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(borderColor));
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }
        
    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public Container getParentFrame() {
        return parentFrame;
    }
    
    
    
}
