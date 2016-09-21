package alanutilites.util.sound;

import javax.sound.sampled.*;
import java.io.*;

/**
 * 
 * @author Alan Tsui
 */
public class Recorder implements Serializable{
    public static void main(String args[]){
        Recorder record = new Recorder();
        record.start(new File("File Path"));
    }
    private static final long serialVersionUID = 1337570192837467l;
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine line;
    
    public Recorder(){
        
    }
    
    /**
     * Defines an audio format
     */
    private static AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 2;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
     
    /**
     * Captures the sound and records it to a WAV file for a certain amount of time
     * @param file  the path where you want to save the file (include name and extension)
     * @param milliseconds  the amount of time you want the recording to be
     */
    public void start(File file, long milliseconds){
        Thread stopper = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException ex) {
                    
                }
                finish();
            }
        });
        stopper.start();
        start(file);
    }
 
    /**
     * Captures the sound and records it to a WAV file
     * @param file  the path where you want to save the file (include name and extension)
     */
    public void start(File file) {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
            }
            else{
                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();   // start capturing


                System.out.println("Start capturing...");

                AudioInputStream ais = new AudioInputStream(line);

                System.out.println("Start recording...");

                // start recording
                AudioSystem.write(ais, fileType, file);
            }
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        line.stop();
        line.close();
    }
}
