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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Great for game timers
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Timer{
    private TimerRunnable runnable;
    private Thread thread;
    private boolean running;
    private ArrayList<ActionListener> listeners;
    
    private int timeToTake;
    
    private long startTime;
    private long acum;
    private long timeItTookTotal;
    private long dif;
    private long timeItTook;
    private int delay;
    
    /**
     * Creates a timer
     * @param delay  the amount of time the action method gets called(delay needs to be in milliseconds)
     * @param listener
     */
    public Timer(int delay, ActionListener listener){
        listeners = new ArrayList<>();
        timeToTake = delay;
        runnable = new TimerRunnable();
        thread = new Thread(runnable);
        thread.start();
        addListener(listener);
    }
    
    /**
     * Starts/Resumes the timer
     */
    public void start(){
        if(!running){
            startTime = System.nanoTime();
            running = true;
        }
    }
    
    /**
     * Stops/Pauses the timer
     */
    public void stop(){
        if(running){
            startTime = 0;
            acum = 0;
            timeItTookTotal = 0;
            dif = 0;
            timeItTook = 0;
            delay = 0;
            running = false;
        }
    }
    
    public void restart(){
        if(isRunning()){
            stop();
            start();
        }
    }
    
    /**
     * Shows whether the timer is running
     * @return  returns true if the timer is running else returns false
     */
    public boolean isRunning(){
        return running;
    }
    
    /**
     * Set the amount of time the action method gets called
     * @param delay  the amount of time in milliseconds
     */
    public void setDelay(int delay){
        this.timeToTake = delay;
    }
    
    /**
     * Returns the delay set for the timer
     * @return  returns the delay set for the timer
     */
    public int getDelay(){
        return timeToTake;
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
    
    private void fireActionPerformed(){
        for(int i=0;i<listeners.size();i++){
            listeners.get(i).actionPerformed(new ActionEvent(Timer.this, 0, "ActionCommand",
                                                    System.currentTimeMillis(),
                                                    0));
        }
    }
    
    private void runnable(){
        startTime = System.nanoTime();
        while(true){
            System.out.print("");
            if(running){
                fireActionPerformed();
                
                acum += timeToTake;
                
                timeItTook = (acum - ((System.nanoTime() - startTime) / 1000000));
                timeItTookTotal += timeItTook;
                
                dif = (timeToTake - timeItTook);
                delay = (int) (timeToTake - dif);

                if (delay < 1) {
                    delay = 1;
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
                }        
            }
        }
    }
    
    private class TimerRunnable implements Runnable{
        @Override
        public void run() {
            runnable();
        }
    }
}
