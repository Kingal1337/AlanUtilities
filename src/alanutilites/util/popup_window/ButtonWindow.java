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
import java.awt.Point;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import static javax.swing.SwingUtilities.convertPointFromScreen;
import javax.swing.Timer;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public abstract class ButtonWindow extends PopupWindow implements ActionListener,MouseMotionListener,MouseListener{
    private static final long serialVersionUID = 133757053875638l;
    private String[] buttonNames;
    private Color foregroundColor;
    private Color hoverColor;
    private Font font;
    private int length;
    private int stringHeight;
    private int lineGap;
    private int selectedIndex;
    
    private Timer timer;
    
    private int mouseY;
    public ButtonWindow(Font font, String[] buttonNames,int lineGap) {
        super(0,0,0,0);
        this.font = font;
        this.buttonNames = buttonNames;
        this.lineGap = lineGap;
        
        timer = new Timer(20, this);
        selectedIndex = -1;
        hoverColor = Color.LIGHT_GRAY;
        foregroundColor = Color.BLACK;
        stringHeight = Text.getHeightOfString(font);
        length = Text.getLargestNumber(buttonNames, font);
        setWidth(length);
        setHeight((stringHeight+lineGap)*buttonNames.length);
        getPanel().addMouseListener(this);
        getPanel().addMouseMotionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        java.awt.Point point = pointerInfo.getLocation();
        convertPointFromScreen(point, getPanel());
        if(point.x < 0 || point.y < 0 || point.x > getPanel().getWidth() || point.y > getPanel().getHeight()){
            update(null,false);
        }
        getPanel().repaint();
    }
    
    @Override
    public void update(Point mouseXY, boolean show){
        if(show){
            timer.start();
        }
        else{
            timer.stop();
        }
        super.update(mouseXY,show);
    }
    
    @Override
    public void paintMethod(Graphics2D gd) {       
        int increment = stringHeight+lineGap;
        int x = 0;
        int y = 0;
        gd.setFont(font);
        for(int i=0;i<buttonNames.length;i++){
            if(selectedIndex == i){
                gd.setColor(hoverColor);
                gd.fillRect(x, y, length, stringHeight+lineGap);
            }
            gd.setColor(foregroundColor);
            gd.drawString(buttonNames[i],x,(y+stringHeight)+(lineGap/2));
            y+=increment;            
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();
        convertPointFromScreen(point, getPanel());
//        System.out.println(getPanel());
        mouseY = (int) point.getY();
//        System.out.println(point);
        
        selectedIndex = mouseY/(stringHeight+lineGap);
        getPanel().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();
        convertPointFromScreen(point, getPanel());
        mouseY = (int) point.y;
        
        selectedIndex = mouseY/(stringHeight+lineGap);
        selector(selectedIndex);
        update(null,false);
        timer.stop();
        
    }
    
    

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        update(new Point(getX(),getY()),true);
        timer.start();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Testy");
        update(null,false);
        timer.stop();
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }
    
    public void setForegroundColor(Color foregroundColor){
        this.foregroundColor = foregroundColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }
    
    public void setHoverColor(Color hoverColor){
        this.hoverColor = hoverColor;
    }
    
    public abstract void selector(int selectedIndex);
    
    
    
}
