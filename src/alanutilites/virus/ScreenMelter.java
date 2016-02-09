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
package alanutilites.virus;

import alanutilites.util.random.Random;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class ScreenMelter extends Frame{
    private String password;
    private String realPassword;
    
    
    private int hours;
    private int seconds;
    private int minutes;
    
    private Timer startTimer;
    
    private BufferedImage screen;
    private ArrayList<Columns> allColumns;
    private ScreenMelterPanel panel;
    private int pixelSize;
    
    
    /**
     * Initalizes a new ScreenMelter
     * @param color  The color of the background
     * @param pixelSize  the pixel size of the columns (Smaller looks cooler)
     * @param speed  the speed the screen melts
     * @param realPassword  the password to disable it
     */
    public ScreenMelter(Color color, int pixelSize, int speed, String realPassword){
        this.realPassword = realPassword;
        this.pixelSize = pixelSize;
        password = "";
        allColumns = new ArrayList<>();
        
        panel = new ScreenMelterPanel(color);
        add(panel);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        KeyboardFocusManager manager
                = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyDispatcher());

    }
    
    /**
     * Actions
     */
    @Override
    public void action(){
        super.action();
        try{
            int randomColumn = Random.randomNumber(0,allColumns.size()-1);
            int randomNumber = Random.randomNumber(1,5);
            allColumns.get(randomColumn).getPoint().y+=randomNumber;
            repaint();
        }catch(Exception e){
            System.err.println("SEVERE ERROR");
        }
    }
    
    public void start(int hoursTillStart, int minutesTillStart, int secondsTillStart){
        if(!isStarted()){
            setStarted(true);
            hours = hoursTillStart;
            minutes = minutesTillStart;
            seconds = secondsTillStart;
            startTimer = new Timer(1000, new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {  
                    startAction();
                }
                
            });
            startTimer.start();
        }
    }
    
    private void startAction(){              
        System.out.println("Hours "+hours+" Minutes "+minutes+" Seconds "+seconds);
        if(seconds <= 0 && minutes > 0){
            minutes--;
            seconds = 60;
        }
        if(minutes <= 0 && hours > 0){
            minutes = 60 ;
            hours --;
        }
        if(seconds == 0 && minutes == 0 && hours == 0){
            startTimer.stop();
            start();
        }
        seconds--;
    }
    
    @Override
    public void start(){
        if(screen == null){
            Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
            try {
                screen = new Robot().createScreenCapture(new Rectangle(0,0,dimensions.width,dimensions.height));
            } catch (AWTException ex) {}
            for(int i=0;i<dimensions.width;i+=pixelSize){
                int pixelSizeToUse = pixelSize;
                if(i+pixelSize > dimensions.width){
                    pixelSizeToUse = dimensions.width-i;
                }
                allColumns.add(new Columns(screen.getSubimage(i, 0, pixelSizeToUse, dimensions.height),new Point(i,0)));
            }
        }
        super.start();
        setTimerSpeed(1);
    }
    
    @Override
    public void stop(){
        screen = null;
        super.stop();
    }
    
    public void reset(){
//        if(is){
//            
//        }
    }
    
    public void restart(){
        stop();
        reset();
        start();
    }
    
    private class ScreenMelterPanel extends JPanel{
        private Color backgroundColor;
        public ScreenMelterPanel(Color backgroundColor){
            this.backgroundColor = backgroundColor;
            setBackground(backgroundColor);
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D gd = (Graphics2D)g;
            
            for (int i=0;i<allColumns.size();i++) {
                gd.drawImage(allColumns.get(i).getImage(), null, allColumns.get(i).getPoint().x, allColumns.get(i).getPoint().y);
            }
        }
    }
    
    private class Columns{
        private BufferedImage image;
        private Point point;
        
        public Columns(BufferedImage image,Point point){
            this.image = image;
            this.point = point;
        }

        public BufferedImage getImage() {
            return image;
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }
        
    }
    
    private class KeyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_TYPED) {
                char key = e.getKeyChar();
                int keyButton = e.getKeyChar();
                if(keyButton == KeyEvent.VK_ESCAPE){
                    password = "";
                }
                if(keyButton == KeyEvent.VK_ENTER){
                    if(password.equals(realPassword)){
                        stop();
                    }
                    else{
                        password = "";
                    }
                }
                else if(keyButton!=KeyEvent.VK_ESCAPE && keyButton != KeyEvent.VK_ENTER){
                    password+=key;                        
                }

            }
            return false;
        }
    }
}
