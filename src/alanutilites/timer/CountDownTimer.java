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
package alanutilites.timer;

import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class CountDownTimer implements ActionListener{    
    private long hours;
    private long minutes;
    private long seconds;
    
    private Timer timer;
    
    private ArrayList<ActionListener> listeners;

    public CountDownTimer(long hours, long minutes, long seconds, ActionListener listener) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        timer = new Timer(1000, this);
        
        listeners = new ArrayList<>();
        addListener(listener);
        
    }
    
    private void countDown(){
        countDownAction(hours, minutes, seconds);
        if(seconds <= 0 && minutes > 0){
            minutes -- ;
            seconds = 60;
        }
        if(minutes <= 0 && hours > 0){
            minutes = 60 ;
            hours --;
        }
        if(seconds == 0 && minutes == 0 && hours == 0){
            forceStart();
            return;
        }
        seconds--;
    }
    
    public void countDownAction(long hours, long minutes, long seconds){}

    @Override
    public void actionPerformed(ActionEvent e) {
        countDown();
    }
    
    private void fireActionPerformed(){
        for(int i=0;i<listeners.size();i++){
            listeners.get(i).actionPerformed(new ActionEvent(CountDownTimer.this, 0, "ActionCommand",
                                                    System.currentTimeMillis(),
                                                    0));
        }
    }
    
    /**
     * Adds a listener
     * @param listener  the listener 
     */
    public void addListener(ActionListener listener){
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }
    
    /**
     * Removes a listener
     * @param listener  the listener 
     */
    public void removeListener(ActionListener listener){
        listeners.remove(listener);
    }
    
    public void start(){
        timer.start();
    }
    
    public void forceStart(){
        stop();
        fireActionPerformed();
    }
    
    public void stop(){
        timer.stop();
    }
    
    public boolean isRunning(){
        return timer.isRunning();
    }
    
    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }
    
}
