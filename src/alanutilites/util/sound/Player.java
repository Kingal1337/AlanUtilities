package alanutilites.util.sound;

import alanutilites.util.FileManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * @author Alan Tsui
 */
public class Player {
    public static void main(String[] args) throws InterruptedException {
        Player player = new Player();
        player.setAudio(new File("File Path"));
        player.loop(true);
        player.pause();
        System.out.println(player.getTotalTime());
        Thread.sleep(5000);
        player.play();
        player.pause();
        Thread.sleep(5000);
        player.play();
    }
    private Clip clip;
    private FloatControl control;
    private Thread thread;
    private boolean debug;
    private boolean loop;
    
    /**
     * Creates a new Player to play sound
     * This only supports 
     */
    public Player(){
        Runner runner = new Runner();        
        thread = new Thread(runner);
        thread.start();
    }
    
    /**
     * Sets the current audio to play
     * @param path  the path of the file
     * @param internalPath  make true if the path is an internal path
     */
    public void setAudio(String path, boolean internalPath){
        try {
            setAudio(FileManager.toByteArray(path, internalPath));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Sets the current audio to play
     * @param file  the file
     */
    public void setAudio(File file){
        setAudio(file.getAbsolutePath(), false);
    }
    
    /**
     * Sets the current audio to play
     * @param array  the byte array
     */
    public void setAudio(byte[] array){
        try {
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new ByteArrayInputStream(array));
            clip.open(ais);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        } catch (IOException e) {
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Sound: Line Unavailable: " + e);
        }
        float newValue = control != null ? control.getValue() : (float)calcVolume(100);
        control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(newValue);
    }
    
    /**
     * Seek to the amount of seconds
     * @param seconds  seconds to seek
     */
    public void seek(long seconds){
        if(clip != null){
            clip.setMicrosecondPosition(seconds*1000000);
        }
        else{
            debugMessage("Audio has not been set!");
        }
    }
    
    /**
     * Gets the elapsed time
     * @return  returns the elapsed time in seconds
     * if audio has not been set returns 0
     */
    public long getElapsedTime(){
        if(clip != null){
            return clip.getMicrosecondPosition()/1000;
        }
        return 0;
    }
    
    /**
     * Gets the total time of the audio
     * @return  returns total time in seconds
     * if audio has not been set returns 0
     */
    public long getTotalTime(){
        if(clip != null){
            return clip.getMicrosecondLength()/1000;
        }
        return 0;
    }
    
    /**
     * Set the volume from 1 - 100
     * @param level  the level
     * @return  returns true if the volume has been set else returns false
     */
    public boolean setVolume(int level){
        if(control != null){
            if(level >= 0 && level <= 100){
                control.setValue((float) calcVolume(level));
                System.out.println((float) calcVolume(level));
                return true;
            }
            else{
                debugMessage("Volume : 0 <= level <= l00!");
                return false;
            }
        }
        else{
            debugMessage("Audio has not been set!");
        }
        return false;
    }
    
    /**
     * Plays the audio from the beginning
     */
    public void play() {
        if(clip != null){
            if(loop){
                startLooping();
            }
            clip.setFramePosition(0);
            clip.start();
        }
        else{
            debugMessage("Audio has not been set!");
        }
//        clip.drain();
    }
    
    /**
     * Resumes the audio from it current position
     */
    public void resume() {
        if(clip != null){
            if(loop){
                startLooping();
            }
            clip.start();
        }
    }
    
    /**
     * Pauses the audio at its current position
     */
    public void pause() {
        stop();
    }
    
    private void startLooping(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    private void stopLooping(){
        clip.loop(0);
    }
    
    /**
     * Loops the audio
     * @param loop  
     * true - so the audio will continue looping
     * false - to stop looping
     */
    public void loop(boolean loop) {
        this.loop = loop;
        if(clip != null && clip.isRunning()){
            if(loop){
                startLooping();
            }
            else{
                stopLooping();
            }
        }
        else{
            debugMessage("Audio has not been set!");       
        }
    }

    /**
     * Stops the audio
     */
    public void stop() {
        if(clip != null){
            if(clip.isRunning() && clip.isActive()){
                clip.stop();
            }
            else{
                debugMessage("Player not running");
            }
        }
        else{
            debugMessage("Audio has not been set!");       
        }
    }
    
    private double calcVolume(double number) {
        return -80 + ((86 * number) / 100);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    private void debugMessage(String message){
        if(debug){
            System.err.println(message);
        }
    }
    
    /**
     * Used when there is no GUI
     * if there is no GUI than the program will just close without this
     */
    private class Runner implements Runnable{
        public Runner(){
            
        }

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
