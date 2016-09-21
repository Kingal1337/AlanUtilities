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

import alanutilites.timer.Timer;
import alanutilites.util.SystemUtil;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Frame extends JFrame implements ActionListener{
    private AltTabStopper stopper;
    private boolean allowAltTab;
    private GraphicsDevice gd;
    
    private boolean debugMode;
    private boolean debugMessages;
    
    private Timer timer;
    private boolean terminateOnClose;
    private boolean started;
    public Frame(){        
        stopper = new AltTabStopper(this);
        allowAltTab = true;
        
        setAlwaysOnTop(true);
        setUndecorated(true);
        setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        timer = new Timer(20, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        action();
    }
    
    public void action(){
        if(!debugMode){
            setFocusableWindowState(true);
            requestFocus(true);
            setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH );  
        }
    }
    
    public void start(){
        started = true;
        if(allowAltTab && !debugMode){
            stopper.start();
        }
        setVisible(true);
        if(SystemUtil.isMac()){
            gd.setFullScreenWindow(this);
        }
        timer.start();
    }
    
    public void stop(){
        started = false;
        timer.stop();
        setVisible(false);
        stopper.terminate();
        if(terminateOnClose){
            System.exit(0);
        }
        gd.setFullScreenWindow(null);
    }
    
    public void debugMode(boolean debug){
        if(!isVisible()){
            if(!started){
                debugMode = debug;
                if(debug){
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else{
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
            else{
                debugMessage("Program already running, cannot change debug state");
            }
        }
        else{
            debugMessage("Frame is visible, cannot change debug state");
        }
    }
    
    public void altTabStopper(boolean altTab){
        if(started){
            debugMessage("Program already running, cannot change boolean state");
        }
        else{
            allowAltTab = altTab;
        }
    }
    
    public void setTimerSpeed(int millisec){
        timer.setDelay(millisec);
    }

    public boolean isDebugMode() {
        return debugMode;
    }
    
    public void setTerminateOnClose(boolean terminate){
        terminateOnClose = terminate;
    }

    public boolean isDebugMessages() {
        return debugMessages;
    }

    public void setDebugMessages(boolean debugMessages) {
        this.debugMessages = debugMessages;
    }

    public boolean hasStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
    private void debugMessage(String message){
        if(debugMessages){
            System.err.println(message);
        }
    }
}
