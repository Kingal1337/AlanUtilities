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

import alanutilites.util.text.Text;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class FileManager {
    
    /**
     * Do not let anyone instantiate this class.
     */
    private FileManager(){}
    
    /**
     * Creates a folder in a specific path
     * @param folderPath  the parent path of where the folder needs to be
     * @param folderName  the name of the folder
     * @return  returns true if the the folder is created or is already created else returns false
     */
    public static boolean createFolder(String folderPath, String folderName){
        return createFolder(new File(folderPath+"/"+folderName));
    }
    
    /**
     * Creates a folder in a specific path
     * @param file  the file
     * @return  returns true if the the folder is created or is already created else returns false
     */
    public static boolean createFolder(File file){
        return !file.exists() ? file.mkdir() : true;
    }
    
    /**
     * copies a folder/file
     * @param src  the folder/file that you want to copy
     * @param dest  the place where you want the copied folder to be
     * @throws IOException  throws an IOException if the parameters are null or doesn't exist
     */
    public static void copy(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }
            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                copy(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }
    
    /**
     * Deletes a folder and everything in it or a file
     * @param file  the path where the folder/file is in
     * @throws IOException  throws an IOException if the file is null or doesn't exist 
     */
    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();

                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            file.delete();
        }
    }
    
    /**
     * Gets all the files in a specific folder
     * @param filePath  the filePath of the folder
     * @param certainExtensions  if there needs to be a certain extension of files than put an extension else leave blank (Leave period out)
     * @return  returns null if filePath is not a folder or the folder does not exist else returns all files in the folder
     */
    public static File[] getAllFiles(String filePath, String[] certainExtensions){
        return getAllFiles(new File(filePath),certainExtensions);
    }
    
    /**
     * Gets all the files in a specific folder
     * @param file  the file
     * @param certainExtensions  if there needs to be a certain extension of files than put an extension else leave blank (Leave period out)
     * @return  returns null if filePath is not a folder or the folder does not exist else returns all files in the folder
     */
    public static File[] getAllFiles(File file, String[] certainExtensions){
        
        if(file.isDirectory()){
            if(certainExtensions == null){
                return file.listFiles();
            }
            else{
                return file.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        for(int i=0;i<certainExtensions.length;i++){
                            if(name.toLowerCase().endsWith("."+certainExtensions[i])){
                                return name.toLowerCase().endsWith("."+certainExtensions[i]);
                            }
                        }
                        return false;
                    }
                });
            }
        }
        return null;
    }
    
    /**
     * gets the next possible name
     * @param folderPath  the folderpath where all the files are located
     * @param defaultStartName  the default name of the image
     * @param spacer  what spaces out the name
     * @param extension  the extension that needs to be used
     * @return  returns the next possible name else returns the defaultStartName
     */
    public static String getName(String folderPath, String defaultStartName, String spacer, String extension) {
        File folderPathLocation = new File(folderPath);
        String nameThatShouldBeUsed = null;
        int counter = 0;
        if (folderPathLocation.isDirectory()) {
            File[] allFiles = getAllFiles(folderPath, new String[]{extension});
            if(allFiles.length != 0){
                for (File allFile1 : allFiles) {
                    if (allFile1.getName().equalsIgnoreCase(defaultStartName + spacer + counter + "." + extension)) {
                        counter++;                            
                    } else {

                    }
                }
                if(counter == 0){
                    nameThatShouldBeUsed = defaultStartName + "." + extension;
                }
                else{
                    nameThatShouldBeUsed = defaultStartName + spacer + "(" + counter + ")" + "." + extension;
                }
            } else {
                nameThatShouldBeUsed = defaultStartName + spacer + "." + extension;
            }
            return nameThatShouldBeUsed;
        } else {
            return defaultStartName + "." + extension;
        }
    }
    
    /**
     * Checks the folder to see if there is a file already named
     * @param filePath  the parent path to the file
     * @param name  the name of the file
     * @param extension  the extension being used by the file
     * @return  returns true if there is a file already named with the variable name else returns false
     */
    public static boolean isAlreadyNamed(String filePath,String name,String extension) {
        File file = new File(filePath);
        if(file.isDirectory()){
            File[] allFiles = getAllFiles(filePath, new String[]{extension});
            if(allFiles.length == 0){
                return false;
            }
            for(int i = 0;i<allFiles.length;i++){
                if(Text.removeExtension(allFiles[i].getName()).equalsIgnoreCase(name)){
                    return true;
                }
            }
            return false;
        }
        else{
            return true;
        }
    }
    
    /**
     * Reads an InputStream and returns all data
     * @param in  InputStream that needs to be read
     * @param charset  the name of a supported {@link java.nio.charset.Charset charset}
     * @return  Returns string array with all data 
     * @throws IOException  if IOException occurs
     */
    public static String[] readInputStream(InputStream in, String charset) throws IOException{
        ArrayList<String> allStrings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, charset))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                allStrings.add(sCurrentLine);
            }
            br.close();
        }
        return allStrings.toArray(new String[allStrings.size()]);
    }
    
    /**
     * Reads a file with text in it
     * @param filePath  the file path to the file you need to read
     * @param charset  the name of a supported {@link java.nio.charset.Charset charset}
     * @return  if file path doesn't exist then returns with an empty array else returns an array of text
     */
    public static String[] readFile(String filePath, String charset){
        return readFile(new File(filePath), charset);
    }
    
    /**
     * Reads a file with text in it
     * @param file  the file
     * @param charset  the name of a supported {@link java.nio.charset.Charset charset}
     * @return  if file path doesn't exist then returns with an empty array else returns an array of text
     */
    public static String[] readFile(File file, String charset){
        try {
            return readInputStream(new FileInputStream(file), charset);
        } catch (FileNotFoundException ex) {} catch (IOException ex) {}
        return null;
    }
    
    /**
     * Saves an array of text
     * @param filePath  the file path you want to save it to
     * @param contents  the contents you need to put in the file (Each index is a new line)
     */
    public static void saveFile(String filePath, String[] contents) {
        saveFile(new File(filePath),contents);
    }
    
    /**
     * Saves an array of text
     * @param file  the file
     * @param contents  the contents you need to put in the file (Each index is a new line)
     */
    public static void saveFile(File file, String[] contents) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i=0;i<contents.length;i++){
                bw.write(contents[i]);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Reads an InputStream and returns all data
     * @param in  InputStream that needs to be read
     * @return  Returns byte array with all data 
     * @throws IOException  if IOException occurs
     */
    public static byte[] toByteArray(InputStream in) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = in.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            
        }
        byte[] bytes = bos.toByteArray();
        in.close();
        bos.close();
        return bytes;
    }
    
    /**
     * Gets a byte array
     * @param filePath  the file path where the byte array is location
     * @param internalPath  make true if the path is an internal path
     * @return  returns a byte array
     * @throws FileNotFoundException  throws Exception if the file does not exist
     * @throws IOException  throws IOException if cannot read file
     */
    public static byte[] toByteArray(String filePath, boolean internalPath) throws FileNotFoundException, IOException{
        return internalPath ? toByteArray(FileManager.class.getResourceAsStream(filePath)) : toByteArray(new FileInputStream(filePath));
    }
    
    /**
     * Gets a byte array
     * @param file  the file
     * @return  returns a byte array
     * @throws FileNotFoundException  throws Exception if the file does not exist
     * @throws IOException  throws IOException if cannot read file
     */
    public static byte[] toByteArray(File file) throws FileNotFoundException, IOException{
        return toByteArray(new FileInputStream(file));
    }
    
    /**
     * Saves a byte array
     * @param filePath  the file path where you want the byte array to be
     * @param bytes  the bytes that need to be saved
     * @throws FileNotFoundException  throws Exception if there is no name in the file
     * @throws IOException  throws IOException if cannot save array
     */
    public static void saveByteArray(String filePath, byte[] bytes) throws FileNotFoundException, IOException{
        saveByteArray(new File(filePath), bytes);
    }
    
    /**
     * Saves a byte array
     * @param file  the file
     * @param bytes  the bytes that need to be saved
     * @throws FileNotFoundException  throws Exception if there is no name in the file
     * @throws IOException  throws IOException if cannot save array
     */
    public static void saveByteArray(File file, byte[] bytes) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
    
    /**
     * Saves an image to a folder
     * @param image  the image that you want to save
     * @param extension  the extension of the image without the period. if there is no extension it will default to PNG
     * @param filePath  the parent path
     * @param fileName  the name you want the image to be
     * @throws IOException  throws an IOException if the image cannot be saved
     */
    public static void saveImage(Image image,String extension, String filePath, String fileName) throws IOException{       
        saveImage(image, new File(filePath+"/"+fileName+"."+extension), extension);
    }
    
    /**
     * Saves an image to a folder
     * @param image  the image that you want to save
     * @param file  the file
     * @param extension  the extension of the image without the period. if there is no extension it will default to PNG
     * @throws IOException  throws an IOException if the image cannot be saved
     */
    public static void saveImage(Image image,File file,String extension) throws IOException{       
        ImageIO.write((BufferedImage)image, extension == null || "".equals(extension) ? "PNG" : extension, file);
    }
    
    /**
     * Saves an image to a folder
     * @param image  the image that you want to save
     * @param extension  the extension of the image without the period. if there is no extension it will default to PNG
     * @param filePath  the parent path
     * @param fileName  the name you want the image to be
     * @throws IOException  throws an IOException if the image cannot be saved
     */
    public static void saveImage(BufferedImage image,String extension, String filePath, String fileName) throws IOException{       
        saveImage(image, new File(filePath+"/"+fileName+"."+extension), extension);
    }
    
    /**
     * Saves an image to a folder
     * @param image  the image that you want to save
     * @param file  the file
     * @param extension  the extension of the image without the period. if there is no extension it will default to PNG
     * @throws IOException  throws an IOException if the image cannot be saved
     */
    public static void saveImage(BufferedImage image,File file,String extension) throws IOException{       
        ImageIO.write(image, extension == null || "".equals(extension) ? "PNG" : extension, file);
    }
    
    /**
     * Saves an image to a folder
     * @param image  the image that you want to save
     * @param extension  the extension of the image without the period. if there is no extension it will default to PNG
     * @param filePath  the parent path
     * @param fileName  the name you want the image to be
     * @throws IOException  throws an IOException if the image cannot be saved
     */
    public static void saveImage(ImageIcon image,String extension, String filePath, String fileName) throws IOException{       
        saveImage(image, new File(filePath+"/"+fileName+"."+extension), extension);
    }
    
    /**
     * Saves an image to a folder
     * @param image  the image that you want to save
     * @param file  the file
     * @param extension  the extension of the image without the period. if there is no extension it will default to PNG
     * @throws IOException  throws an IOException if the image cannot be saved
     */
    public static void saveImage(ImageIcon image,File file,String extension) throws IOException{       
        ImageIO.write((BufferedImage) image.getImage(), extension == null || "".equals(extension) ? "PNG" : extension, file);
    }
    
    /**
     * Gets an image from a specific path
     * @param filePath  the file path where the image is location
     * @return  returns an ImageIcon if the file path exists and if not a directory else returns null
     */
    public static ImageIcon readImage(String filePath){
        return readImage(new File(filePath));
    }
    
    /**
     * Gets an image from a specific path
     * @param file  the file
     * @return  returns an ImageIcon if the file path exists and if not a directory else returns null
     */
    public static ImageIcon readImage(File file){
        return file.exists() ?  file.isFile() ? new ImageIcon(file.getAbsolutePath()) : null : null;
    }
    
    /**
     * Tells what extension the file uses
     * @param path  the file path or the file name
     * @return  returns the extension of the file if the file has an extension if it doesn't returns empty string
     */
    public static String getExtension(String path){
        return path.contains(".") ? path.substring(path.lastIndexOf(".")+1) : "";
    }
}
