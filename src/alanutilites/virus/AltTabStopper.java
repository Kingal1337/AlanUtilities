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

import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class AltTabStopper{
    private boolean working;
    private boolean started;
    private boolean running;
    private JFrame frame;

    public AltTabStopper(JFrame frame) {
        this.frame = frame;
        working = true;
        running = false;
        AltTabRunnable runnable = new AltTabRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }
    
    public void start(){
        if(started == false){
            started = true;
        }
        running = true;
    }
    
    public void stop(){
        running = false;
    }
    
    public void terminate(){
        working = false;
    }
    
    private class AltTabRunnable implements Runnable{
        @Override
        public void run() {
            try {
                Robot robot = new Robot();
                while (working) {
                    System.out.print("");
                    if(running){
                        robot.keyRelease(KeyEvent.VK_AT);
                        robot.keyRelease(KeyEvent.VK_TAB);
                        frame.requestFocus();
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
