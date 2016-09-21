/*
 * The MIT License
 *
 * Copyright 2016 Alan Tsui.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package alanutilites.test;

import alanutilites.timer.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Alan Tsui
 */
public class Fader extends JPanel implements ActionListener{
    private Timer timer;
    private int counter;
    private int interval;
    private int speed;
    private int timeToWait;
    private int tickCounter;
    private ImageIcon image;
    public Fader(ImageIcon image, int speed, int timeToWait){
        this.image = image;
        interval = -2;
        counter = 300;
        this.timeToWait = timeToWait;
        this.speed = speed;
        setBackground(Color.BLACK);
        timer = new Timer(speed, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter+=interval;
        if(counter <= 0){
            counter = 0;
            tickCounter += speed;
            if(tickCounter >= timeToWait){
                interval = 2;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        gd.drawImage(image.getImage(), (getSize().width-image.getIconWidth())/2, (getSize().height-image.getIconHeight())/2, null);
        g.setColor(new Color(0,0,0,counter > 255 ? 255 : counter < 0 ? 0 : counter));
        g.fillRect(0, 0, getSize().width, getSize().height);
    }
    
    public void skip(){
        tickCounter = timeToWait;
    }
        
}
