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
package alanutilites.util;

import alanutilites.util.random.Random;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Alan Tsui
 */
public class Arrays {
    public static final char[] LOWER_CASE_LETTERS = generateArray('a',(char)('z'+1));
    public static final char[] UPPER_CASE_LETTERS = generateArray('A',(char)('Z'+1));
    public static final char[] PUNCTUATION = combine(combine(combine(generateArray('!',(char)('/'+1)),generateArray(':',(char)('@'+1))),generateArray('[',(char)('`'+1))),generateArray('{',(char)('~'+1)));
    
    /**
     * Generates an array that corresponds with acsii codes
     * @param min  the smallest number
     * @param max  the biggest number
     * @return  returns an array of characters that are in-between the min-max
     */
    public static char[] generateArray(char min, char max){
        char[] alphabet = new char[max-min];
        for(int i=0;i<alphabet.length;i++){
            alphabet[i] = (char)(min+i);                        
        }        
        return alphabet;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static Object[] combine(Object[] a, Object[] b){
        Object[] c = createArray(a.getClass().getComponentType(), a.length+b.length);
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static String[] combine(String[] a, String[] b){
        String[] c = new String[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static byte[] combine(byte[] a, byte[] b){
        byte[] c = new byte[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static char[] combine(char[] a, char[] b){
        char[] c = new char[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static int[] combine(int[] a, int[] b){
        int[] c = new int[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static double[] combine(double[] a, double[] b){
        double[] c = new double[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static long[] combine(long[] a, long[] b){
        long[] c = new long[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static float[] combine(float[] a, float[] b){
        float[] c = new float[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Combines an array with another array
     * @param a  the first array
     * @param b  the second array
     * @return  returns a combined array
     */
    public static short[] combine(short[] a, short[] b){
        short[] c = new short[a.length+b.length];
        for(int i=0;i<a.length;i++){
            c[i] = a[i];
        }
        for(int j=0;j<b.length;j++){
            c[j+a.length] = b[j]; 
        }        
        return c;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(Object[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(String[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(byte[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(char[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(int[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(long[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(double[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(float[] array){
        return array.length == 0;
    }
    
    /**
     * Determines if an array is empty
     * @param array  the array that needs to get checked
     * @return  returns true if the array is empty else returns false
     */
    public static boolean isArrayEmpty(short[] array){
        return array.length == 0;
    }
    
    /**
     * Gets how many actual indexes are filled
     * @param array  the array that needs to get checked
     * @return  returns the number of filled indexes
     */
    public static int getActualSize(Object[] array){
        int counter = 0;
        if(isArrayEmpty(array)){
            return 0;
        }
        for (Object array1 : array) {
            if (array1 != null) {
                counter++;
            }
        }
        return counter;
    }
    
    /**
     * Gets how many actual indexes are filled
     * @param array  the array that needs to get checked
     * @return  returns the number of filled indexes
     */
    public static int getActualSize(String[] array){
        int counter = 0;
        if(isArrayEmpty(array)){
            return 0;
        }
        for (Object array1 : array) {
            if (array1 != null) {
                counter++;
            }
        }
        return counter;
    }
    
    /**
     * Gets how many actual indexes are filled
     * @param array  the array that needs to get checked
     * @return  returns the number of filled indexes
     */
    public static int getActualSize(char[] array){
        int counter = 0;
        if(isArrayEmpty(array)){
            return 0;
        }
        for (Object array1 : array) {
            if (array1 != null) {
                counter++;
            }
        }
        return counter;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(Object[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(String[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(byte[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(char[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(int[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(long[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(double[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(float[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Sees if an index exist
     * @param array  the array that needs to get checked
     * @param index  the index
     * @return  returns true if the index does exist else returns false
     */
    public static boolean doesIndexExist(short[] array, int index){
        return isArrayEmpty(array) ? false : index >= 0 && index < array.length;
    }
    
    /**
     * Converts an object ArrayList to an object array
     * @param array  the ArrayList that needs to be converted
     * @param classType  the type that the array is using
     * @return  returns an object array
     */
    public static Object[] convertToArray(ArrayList<?> array,Class<?> classType){
        Object[] allObjects = createArray(classType, array.size());
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = classType.cast(array.get(i));
        }
        return allObjects;
    }
    
    /**
     * Converts a string ArrayList to a string array
     * @param array  the ArrayList that needs to be converted
     * @return  returns a string array
     */
    public static String[] convertStringToArray(ArrayList<String> array){
        String[] allObjects = new String[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts a byte ArrayList to a byte array
     * @param array  the ArrayList that needs to be converted
     * @return  returns a byte array
     */
    public static byte[] convertByteToArray(ArrayList<Byte> array){
        byte[] allObjects = new byte[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts a char ArrayList to a char array
     * @param array  the ArrayList that needs to be converted
     * @return  returns a char array
     */
    public static char[] convertCharToArray(ArrayList<Character> array){
        char[] allObjects = new char[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts an int ArrayList to an int array
     * @param array  the ArrayList that needs to be converted
     * @return  returns an int array
     */
    public static int[] convertIntToArray(ArrayList<Integer> array){
        int[] allObjects = new int[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts a double ArrayList to an double array
     * @param array  the ArrayList that needs to be converted
     * @return  returns a double array
     */    
    public static double[] convertDoubleToArray(ArrayList<Double> array){
        double[] allObjects = new double[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts a long ArrayList to an long array
     * @param array  the ArrayList that needs to be converted
     * @return  returns a long array
     */   
    public static long[] convertLongToArray(ArrayList<Long> array){
        long[] allObjects = new long[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts a float ArrayList to an float array
     * @param array  the ArrayList that needs to be converted
     * @return  returns a float array
     */   
    public static float[] convertFloatToArray(ArrayList<Float> array){
        float[] allObjects = new float[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts a short ArrayList to a short array
     * @param array  the ArrayList that needs to be converted
     * @return  returns a short array
     */
    public static short[] convertShortToArray(ArrayList<Short> array){
        short[] allObjects = new short[array.size()];
        for(int i=0;i<allObjects.length;i++){
            allObjects[i] = array.get(i);
        }
        return allObjects;
    }
    
    /**
     * Converts an object array to an object ArrayList
     * @param array  the array that needs to be converted
     * @return  returns an object arrayList
     */
    public static ArrayList<Object> convertToArrayList(Object[] array){
        ArrayList<Object> allObjects = new ArrayList<>();
        for (Object array1 : array) {
            allObjects.add(array.getClass().getComponentType().cast(array1));
        }
        return allObjects;
    }
    
    /**
     * Converts a string array to a string ArrayList
     * @param array  the array that needs to be converted
     * @return  returns a string arrayList
     */
    public static ArrayList<String> convertToArrayList(String[] array){
        ArrayList<String> allObjects = new ArrayList<>();
        for (String array1 : array) {
            allObjects.add(array1);
        }
        return allObjects;
    }
    
    /**
     * Converts a byte array to a byte ArrayList
     * @param array  the array that needs to be converted
     * @return  returns a byte arrayList
     */
    public static ArrayList<Byte> convertToArrayList(byte[] array){
        ArrayList<Byte> allObjects = new ArrayList<>();
        for(int i=0;i<array.length;i++){
           allObjects.add(array[i]);
        }
        return allObjects;
    }
    
    /**
     * Converts a char array to a char ArrayList
     * @param array  the array that needs to be converted
     * @return  returns a char arrayList
     */
    public static ArrayList<Character> convertToArrayList(char[] array){
        ArrayList<Character> allObjects = new ArrayList<>();
        for(int i=0;i<array.length;i++){
           allObjects.add(array[i]);
        }
        return allObjects;
    }
    
    /**
     * Converts an int array to an Integer ArrayList
     * @param array  the array that needs to be converted
     * @return  returns an Integer arrayList
     */
    public static ArrayList<Integer> convertToArrayList(int[] array){
        ArrayList<Integer> allObjects = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            allObjects.add(array[i]);
        }
        return allObjects;
    }
    
    /**
     * Converts a long array to a long ArrayList
     * @param array  the array that needs to be converted
     * @return  returns a long arrayList
     */
    public static ArrayList<Long> convertToArrayList(long[] array){
        ArrayList<Long> allObjects = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            allObjects.add(array[i]);
        }
        return allObjects;
    }
    
    /**
     * Converts a double array to a double ArrayList
     * @param array  the array that needs to be converted
     * @return  returns a double arrayList
     */
    public static ArrayList<Double> convertToArrayList(double[] array){
        ArrayList<Double> allObjects = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            allObjects.add(array[i]);
        }
        return allObjects;
    }
    
    /**
     * Converts a float array to a float ArrayList
     * @param array  the array that needs to be converted
     * @return  returns a float arrayList
     */
    public static ArrayList<Float> convertToArrayList(float[] array){
        ArrayList<Float> allObjects = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            allObjects.add(array[i]);
        }
        return allObjects;
    }
    
    /**
     * Converts a short array to a short ArrayList
     * @param array  the array that needs to be converted
     * @return  returns a short arrayList
     */
    public static ArrayList<Short> convertToArrayList(short[] array){
        ArrayList<Short> allObjects = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            allObjects.add(array[i]);
        }
        return allObjects;
    }
    
    /**
     * Turns object array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */
    public static String toString(Object[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns string array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(String[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns byte array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(byte[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns char array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(char[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns int array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(int[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns long array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(long[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns double array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(double[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns float array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(float[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Turns short array into a string
     * @param array  the array that needs 
     * @return returns  returns a string
     */    
    public static String toString(short[] array){
        String neatString = "";
        for(int i=0;i<array.length;i++){
            neatString += array[i];
            if(i >= array.length-1){
                
            }
            else{
                neatString += "\n";
            }
        }
        return neatString;
    }
    
    /**
     * Adds an object to the end of an array
     * @param array  the array that needs to be edited
     * @param object  the object that needs to be added
     * @return  returns a modified array, if array and object types do not match then returns original array
     */
    public static Object[] addObjectToArray(Object[] array,Object object){
        if(array.getClass().getComponentType().isInstance(object)){
            Object[] newArray = createArray(array.getClass().getComponentType(), array.length+1);
            for(int i=0;i<array.length;i++){
                newArray[i] = array.getClass().getComponentType().cast(array[i]);
            }
            newArray[array.length] = array.getClass().getComponentType().cast(object);
            return newArray;
        }
        else{
            return array;
        }
    }
    
    /**
     * Adds a string to the end of an array
     * @param array  the array that needs to be edited
     * @param string  the string that needs to be added
     * @return  returns a modified array, if array and string types do not match then returns original array
     */
    public static String[] addObjectToArray(String[] array,String string){        
        String[] newArray = new String[array.length+1];
        for(int i=0;i<array.length;i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = string;
        return newArray;
    }
    
    /**
     * Adds a byte to the end of an array
     * @param array  the array that needs to be edited
     * @param theByte  the byte that needs to be added
     * @return  returns a modified array, if array and byte types do not match then returns original array
     */
    public static byte[] addObjectToArray(byte[] array,byte theByte){        
        byte[] newArray = new byte[array.length+1];
        for(int i=0;i<array.length;i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = theByte;
        return newArray;
    }
    
    /**
     * Adds an char to the end of an array
     * @param array  the array that needs to be edited
     * @param integer  the char that needs to be added
     * @return  returns a modified array, if array and char types do not match then returns original array
     */
    public static char[] addObjectToArray(char[] array,char integer){        
        char[] newArray = new char[array.length+1];
        for(int i=0;i<array.length;i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = integer;
        return newArray;
    }
    
    /**
     * Adds an int to the end of an array
     * @param array  the array that needs to be edited
     * @param integer  the int that needs to be added
     * @return  returns a modified array, if array and integer types do not match then returns original array
     */
    public static int[] addObjectToArray(int[] array,int integer){        
        int[] newArray = new int[array.length+1];
        for(int i=0;i<array.length;i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = integer;
        return newArray;
    }
    
    /**
     * Adds a long to the end of an array
     * @param array  the array that needs to be edited
     * @param longNumber  the long that needs to be added
     * @return  returns a modified array, if array and longNumber types do not match then returns original array
     */
    public static long[] addObjectToArray(long[] array,long longNumber){        
        long[] newArray = new long[array.length+1];
        for(int i=0;i<array.length;i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = longNumber;
        return newArray;
    }
    
    /**
     * Adds a float to the end of an array
     * @param array  the array that needs to be edited
     * @param floatNumber  the float that needs to be added
     * @return  returns a modified array, if array and floatNumber types do not match then returns original array
     */
    public static float[] addObjectToArray(float[] array,float floatNumber){        
        float[] newArray = new float[array.length+1];
        for(int i=0;i<array.length;i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = floatNumber;
        return newArray;
    }
    
    /**
     * Adds a double to the end of an array
     * @param array  the array that needs to be edited
     * @param doubleNumber  the double that needs to be added
     * @return  returns a modified array, if array and doubleNumber types do not match then returns original array
     */
    public static double[] addObjectToArray(double[] array,double doubleNumber){        
        double[] newArray = new double[array.length+1];
        for(int i=0;i<array.length;i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = doubleNumber;
        return newArray;
    }
    
    /**
     * Adds an object to the next empty spot in the array
     * @param array  the array that if being edited
     * @param object  the object that needs to get added
     * @return  returns the index where the object was placed if array is full then returns -1 and the object will not be added
     */
    public static int addToArray(Object[] array,Object object){
        int added = -1;
        for(int i=0;i<array.length;i++){
            if(array[i] == null){
                array[i] = object;
                added = i;
                break;
            }
            else{
                added = -1;
            }
        }
        return added;
    }
    
    /**
     * Adds a string to the next empty spot in the array
     * @param array  the array that if being edited
     * @param object  the string that needs to get added
     * @return  returns the index where the string was placed if array is full then returns -1 and the string will not be added
     */
    public static int addToArray(String[] array,String object){
        int added = -1;
        for(int i=0;i<array.length;i++){
            if(array[i] == null){
                array[i] = object;
                added = i;
                break;
            }
            else{
                added = -1;
            }
        }
        return added;
    }
    
    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * 
     * @param array  the array the object is in
     * @param object  element to be removed from this list, if present
     */
    public static void removeObjectFromArray(Object[] array,Object object){
        for(int i=0;i<array.length;i++){
            if(array[i].equals(object)){
                array[i] = null;
                break;
            }
        }
    }
    
    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * 
     * @param array  the array the object is in
     * @param object  element to be removed from this list, if present
     */
    public static void removeObjectFromArray(String[] array,String object){
        for(int i=0;i<array.length;i++){
            if(array[i].equals(object)){
                array[i] = null;
                break;
            }
        }
    }
    
    /**
     * Creates an array with a specific type
     * @param <T>  the type 
     * @param clazz  the type of array that you want
     * @param size  the size of the array
     * @return  returns an array with a specific type
     */
    private static <T> T[] createArray(Class<T> clazz, int size) {
        T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
        return array;
    }
    
    /**
     * Swaps 2 objects in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(Object[] array,int index1,int index2){
        Object temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array.getClass().getComponentType().cast(array[index1]);
            array[index1] = array[index2];
            array[index2] = array.getClass().getComponentType().cast(temp);
        }
    }
    
    /**
     * Swaps 2 String in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(String[] array,int index1,int index2){
        String temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Swaps 2 byte in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(byte[] array,int index1,int index2){
        byte temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Swaps 2 char in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(char[] array,int index1,int index2){
        char temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Swaps 2 int in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(int[] array,int index1,int index2){
        int temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Swaps 2 long in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(long[] array,int index1,int index2){
        long temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Swaps 2 double in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(double[] array,int index1,int index2){
        double temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Swaps 2 float in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(float[] array,int index1,int index2){
        float temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Swaps 2 short in the array
     * @param array  the array that needs to be edited
     * @param index1  the first index that needs to get swap
     * @param index2  the second index that needs to get swap
     */
    public static void swap(short[] array,int index1,int index2){
        short temp;
        if(doesIndexExist(array, index1) && doesIndexExist(array, index2)){
            temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(Object[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(String[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(byte[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(char[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(int[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(long[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(double[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(float[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Randomizes an array
     * @param array  the array that needs to be randomized
     */
    public static void randomize(short[] array){
        for(int i=0;i<array.length;i++){
            int randomIndex = Random.randomNumber(0, array.length-1);
            swap(array, i, randomIndex);
        }
    }
    
    /**
     * Converts an object into a byte array (Object Must implement Serializable)
     * @param obj  Object that is going to be converted to a byte array
     * @return  returns a byte array
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            bytes = bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Arrays.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytes;
    }
    
    /**
     * Converts a byte array to an object (Object Must implement Serializable)
     * @param bytes  bytes that are going to be converted to a byte array
     * @return  returns an object
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Arrays.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Arrays.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return obj;
    }
    
    public static void quickSort(int[] array,int low,int high){
        int newLow = low;
        int newHigh = high;
        int pivot = array[low + (high - low)/2];
        while(array[newLow] < pivot){
            newLow ++;
        }
        while(array[newHigh] > pivot){
            newHigh --;
        }
        if(newLow <= newHigh){
            swap(array, newLow, newHigh);
            newLow++;
            newHigh--;
        }
        if(low < newHigh){
            quickSort(array, low, newHigh);
        }
        if(newLow < high){
            quickSort(array,newLow, high);
        }
    }
    
    public static void bubbleSort(Object[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j].hashCode() > array[j+1].hashCode()){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(String[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j].trim().compareTo(array[j+1].trim()) > 0){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(char[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(byte[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(int[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(long[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(double[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(float[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void bubbleSort(short[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                }
            }
        }
    }
    
    public static void sort(Object[] array){
        bubbleSort(array);
    }
    
    public static void sort(String[] array){
        bubbleSort(array);
    }
    
    public static void sort(byte[] array){
        bubbleSort(array);
    }
    
    public static void sort(char[] array){
        bubbleSort(array);
    }
    
    public static void sort(int[] array){
        bubbleSort(array);
    }
    
    public static void sort(long[] array){
        bubbleSort(array);
    }
    
    public static void sort(double[] array){
        bubbleSort(array);
    }
    
    public static void sort(float[] array){
        bubbleSort(array);
    }
    
    public static void sort(short[] array){
        bubbleSort(array);
    }
}