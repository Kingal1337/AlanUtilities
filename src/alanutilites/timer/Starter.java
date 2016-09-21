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
package alanutilites.timer;

import alanutilites.util.time.Time;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public abstract class Starter implements ActionListener{    
    private Time timeToStart;    
    private Time currentTime;
    private Timer timer;
    
    private boolean useExact;
    public Starter(Time time){
        this.timeToStart = time;
        currentTime = Time.getCurrentTime();
        useExact = false;
        timer = new Timer(1000,this);
    }
    
    public void useExactComparing(boolean bool){
        useExact = bool;
    }
    
    public void start(){
        timer.start();
    }
    
    public void stop(){
        timer.stop();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(useExact ? currentTime.exactCompare(timeToStart) : currentTime.estimateCompare(timeToStart)){
            action();
            stop();
        }
        else{
            currentTime.addSeconds(1);
        }
    }
    
    public abstract void action();
}
