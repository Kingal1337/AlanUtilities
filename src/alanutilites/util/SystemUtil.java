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
package alanutilites.util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class SystemUtil {
    /**
     * The OS of your computer
     */
    public static final String OS = System.getProperty("os.name").toLowerCase();
    
    /**
     * The version of your OS
     */
    public static final String OS_VERSION = System.getProperty("os.version");
    
    /**
     * Character that separates components of a file path. This is "/" on UNIX and "\" on Windows
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    /**
     * The username of the current user
     */
    public static final String USER_NAME = System.getProperty("user.name");
    
    /**
     * The local ip of this computer
     */
    public static final String LOCAL_IP = getLocalIP();
    
    /**
     * The name of your computer
     */
    public static final String COMPUTER_NAME = getComputerName();
    
    /**
     * The size of your computer screen;
     */
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
    
    /**
     * Do not let anyone instantiate this class.
     */
    private SystemUtil(){}
    
    /**
     * Sees if the OS is windows
     * @return  returns true is the OS is windows else returns false
     */
    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    /**
     * Sees if the OS is mac
     * @return  returns true is the OS is mac else returns false
     */
    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    /**
     * Sees if the OS is unix
     * @return  returns true is the OS is unix else returns false
     */
    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    /**
     * Sees if the OS is solaris
     * @return  returns true is the OS is solaris else returns false
     */
    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }
    
    /**
     * Takes a screenshot of a certain area
     * @param rectangle  the area that needs to get taken
     * @return  returns the selected area as a BufferedImage
     */
    public static BufferedImage takeScreenShot(Rectangle rectangle){
        try {
            return new Robot().createScreenCapture(rectangle);
        } catch (AWTException ex) {
            Logger.getLogger(SystemUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Takes a screenshot of the entire screen
     * @return  returns the entire screen as a BufferedImage
     */
    public static BufferedImage takeScreenShot(){
        return takeScreenShot(new Rectangle(0,0,SCREEN_SIZE.width,SCREEN_SIZE.height));
    }
    
    /**
     * Runs a command in command prompt/terminal
     * @param command  the command that would be used
     * @return  returns a process
     * @throws java.io.IOException  if IOException occurs
     */
    public static Process runCommand(String command) throws IOException{
        return Runtime.getRuntime().exec(command);        
    }
    
    /**
     * Closes a program 
     * Note : Some programs needs admin to close
     * @param program 
     */
    public static void killProgram(String program){
        try {
            runCommand("taskkill /F /IM " + program).waitFor();
        } catch (Exception e) {
            System.err.println("Error");
        }
    }
    
    /**
     * Opens your default browser with a url
     * @param url  the url that you want to open
     */
    public static void openBrowser(String url) {
        Runtime rt = Runtime.getRuntime();
        try {
            if (isWindows()) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (isMac()) {
                rt.exec("open " + url);
            } else if (isUnix()) {
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx"};
                StringBuilder cmd = new StringBuilder();
                for (int i = 0; i < browsers.length; i++) {
                    cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
                }
                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } else {
                return;
            }
        } catch (Exception e) {
            return;
        }
    }
    
    /**
     * Shuts down your computer
     * @throws RuntimeException  throws RunTimeException if the OS is unsupported
     * @throws IOException  throws IOException if the OS is unsupported
     */
    public static void shutdown() throws RuntimeException, IOException {
        String command;

        if (isUnix() || isMac()) {
            command = "shutdown -h now";
        } else if (isWindows()) {
            command = "shutdown.exe -s -t 0";
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }

        runCommand(command);
        System.exit(0);
    }
    
    /**
     * logs out of your computer
     * @throws RuntimeException  throws RunTimeException if the OS is unsupported
     * @throws IOException  throws IOException if the OS is unsupported
     */
    public static void logout() throws RuntimeException, IOException{
        String command;

        if (isUnix() || isMac()) {
            command = "logout";
        } else if (isWindows()) {
            command = "shutdown.exe -l ";
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }

        runCommand(command);
        System.exit(0);
    }
    
    /**
     * restarts your computer
     * @throws RuntimeException  throws RunTimeException if the OS is unsupported
     * @throws IOException  throws IOException if the OS is unsupported
     */
    public static void restart() throws RuntimeException, IOException {
        String command;

        if (isUnix() || isMac()) {
            command = "reboot";
        } else if (isWindows()) {
            command = "shutdown.exe -r ";
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }

        runCommand(command);
        System.exit(0);
    }
    
    /**
     * hibernates your computer
     * @throws RuntimeException  throws RunTimeException if the OS is unsupported
     * @throws IOException  throws IOException if the OS is unsupported
     */
    public static void hibernate() throws RuntimeException, IOException {
        String command;
         if (isWindows()) {
            command = "shutdown.exe -h ";
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }

        runCommand(command);
    }
    
    /**
     * Lists all users on local computer
     * @return  returns all users in a string array
     * @throws IOException  throws IOException
     */
    public static String[] allUser() throws IOException {
        if(isWindows()){
            Process process = runCommand("net user");
            String[] allData = FileManager.readInputStream(process.getInputStream());
            String allUsersInString = "";
            for(int i=4;i<allData.length-2;i++){
                allUsersInString+=allData[i]+" ";
            }
            return allUsersInString.split("\\s+");
        }
        return new String[0];
    }
    
    public static String[] getProcesses() throws IOException{
        Process process = runCommand("tasklist /svc");
        String[] allData = FileManager.readInputStream(process.getInputStream());
        String allUsersInString = "";
        for(int i=4;i<allData.length-2;i++){
            allUsersInString+=allData[i]+" ";
        }
        return allUsersInString.split("\\s+");
    }
    
    /**
     * Opens command prompt  
     * @throws IOException  throws if command is unavailable
     */
    public static void openCMD() throws IOException{
        if(isWindows()){
            runCommand("cmd.exe /c start");
        }
    }
    
    /**
     * Checks if a user exists
     * @param user  a user to check
     * @return  returns true is the user exist else returns false
     * @throws IOException  throws IOException
     */
    public static boolean doesUserExist(String user) throws IOException{
        if(isWindows()){
            String[] allUsers = allUser();
            for(String userName : allUsers){
                if(userName.equals(user)){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Gets the computer name
     * @return  returns computer name
     */
    public static String getComputerName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(SystemUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return InetAddress.getLoopbackAddress().getHostName();
    }
    
    /**
     * Gets your local IP address
     * @return  returns you local IP address
     */
    public static String getLocalIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(SystemUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return InetAddress.getLoopbackAddress().getHostAddress();
    }
}
