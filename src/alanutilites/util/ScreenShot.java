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
package alanutilites.util;

import alanutilites.util.text.Text;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Creates a screenshot window on how it looks on mac
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class ScreenShot extends JFrame implements Serializable{
    public static void main(String... aergs) throws Exception{
        ScreenShot shot = new ScreenShot("Save Path", "Title", " ");
        Thread.sleep(2000);
        shot.start();
    }
    private static final long serialVersionUID = 1337570192837466l;
    
    private JPanel panel;
    
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    
    private boolean dragging;
    private boolean save;
    
    private int mouseX;
    private int mouseY;
    
    private Rectangle rectangle;
    
    private String filePath;
    
    private int counter;
    
    /**
     * allows making screenshots on windows
     * Note : screenshots on WINDOWS ONLY
     * @param filePath  the file path where the image is going to be saved
     * @param defaultName  the default name of the image
     * @param spacer  what spaces out the name
     * @throws Exception  throws Exception if OS is not supported
     */
    public ScreenShot(String filePath,String defaultName,String spacer) throws Exception{
        if(SystemUtil.isWindows()){
            this.filePath = filePath;
            dragging = false;
            counter = 0;
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(10,10));
            setResizable(false);
            setAlwaysOnTop(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);            
            pack();
            
            panel = new JPanel(){
                @Override
                public void paintComponent(Graphics g){
                    super.paintComponent(g);
                    Graphics2D gd = (Graphics2D)g;

                    gd.setColor(Color.BLACK);
                    Point p1 = new Point(startX,startY);
                    Point p2 = new Point(mouseX,mouseY);
                    rectangle = getRectangle(p1, p2);
                    if(dragging && !save){
                        gd.draw(rectangle);
                    }
                    else if(save){      
                        p1 = new Point(startX,startY);
                        p2 = new Point(endX,endY);
                        rectangle = getRectangle(p1, p2);
                        if(counter == 1){
                            try {
                                saveImage(filePath, FileManager.getName(filePath, defaultName, spacer, "png"));
                            } catch (IOException ex) {
                                Logger.getLogger(ScreenShot.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (AWTException ex) {
                                Logger.getLogger(ScreenShot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            exit();
                        }
                        counter++;
                    }
                }            
            };
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(e.getButton() == MouseEvent.BUTTON1){
                        if(!dragging){
                            dragging = true;
                            startX = mouseX;
                            startY = mouseY;
                        }      
                        else if(dragging){
                            dragging = false;
                            endX = mouseX;
                            endY = mouseY;
                            save = true;
                        }      
                    }
                    if(e.getButton() == MouseEvent.BUTTON3){
                        dragging = false;
                        startX =  0;
                        startY = 0;
                        endX = 0;
                        endY = 0;
                    }
                    repaint();
                }
            });
            panel.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint();
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint();
                }
            });
            panel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if(key == KeyEvent.VK_ESCAPE){
                        exit();
                    }
                }                
            });
            panel.setFocusable(true);
            setBackground(new Color(1.0f,1.0f,1.0f,0.01f));
            panel.setBackground(new Color(1.0f,1.0f,1.0f,0.01f));
            add(panel);
        }
        else{
            throw new Exception("Wrong OS");
        }
    }
    
    /**
     * Resets all variables back to original 
     */
    private void reset(){
        counter = 0;
        save = false;
        dragging = false;
        startX =  0;
        startY = 0;
        endX = 0;
        endY = 0;
        rectangle = null;
    }
    
    /**
     * Stops the screenshot but still able to reuse
     */
    public void exit(){
        setVisible(false);
    }
    
    /**
     * starts the screenshot
     */
    public void start(){
        reset();
        setVisible(true);
    }
        
    /**
     * Removes the frame
     */
    public void stop(){
        this.dispose();
    }
    
    private Rectangle getRectangle(Point point1,Point point2){
        Rectangle r = new Rectangle(point1);
        r.add(point2);
        return r;
    }
    
    private void saveImage(String filePath,String fileName) throws IOException, AWTException{     
        BufferedImage capture = SystemUtil.takeScreenShot(rectangle);
        FileManager.saveImage(new ImageIcon(capture), FileManager.getExtension(fileName), filePath, Text.removeExtension(fileName));
    }
}
