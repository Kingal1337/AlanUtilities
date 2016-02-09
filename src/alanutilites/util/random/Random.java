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
package alanutilites.util.random;

import autil.Arrays;
import java.awt.Color;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Random {
    private static java.util.Random random = new java.util.Random();
    
    /**
     * Do not let anyone instantiate this class.
     */
    private Random(){}
    
    
    /**
     * Gets a random number from a minimum number(inclusive) to a maximum number(inclusive)
     * @param min  the minimum number (inclusive)
     * @param max  the maximum number (inclusive)
     * @return  returns a random number from minimum to maximum. if minimum number is larger than maximum returns 0
     */
    public static int randomNumber(int min,int max){
        return min > max ? 0 : random.nextInt((max+1)-min)+min;
    }
    
    /**
     * Gets a random number from a minimum number(inclusive) to a maximum number(inclusive)
     * @param min  the minimum number (inclusive)
     * @param max  the maximum number (inclusive)
     * @return  returns a random number from minimum to maximum. if minimum number is larger than maximum returns 0
     */
    public static long randomNumber(long min,long max){
        return min > max ? 0 : min+((long)(random.nextDouble()*(max-min)));
    }
    
    /**
     * Generates a random letter from the alphabet
     * @return  returns a random letter
     */
    public static char randomLetter(){
        char[] combineArray = Arrays.combine(Arrays.LOWER_CASE_LETTERS,Arrays.UPPER_CASE_LETTERS);
        return combineArray[randomNumber(0,combineArray.length-1)];
    }
    
    /**
     * Generates a random color
     * @return  returns a random color
     */
    public static Color randomColor(){
        int r = randomNumber(0, 255);
        int g = randomNumber(0, 255);
        int b = randomNumber(0, 255);
        Color color = new Color(r,g,b);
        return color;
    }
    
    /**
     * Generates a random character
     * @return  returns a random character
     */
    public static char randomCharacter(){
        char[] combineArray = Arrays.combine(Arrays.combine(Arrays.LOWER_CASE_LETTERS,Arrays.UPPER_CASE_LETTERS),Arrays.PUNCTUATION);
        return combineArray[randomNumber(0,combineArray.length-1)];
    }
    
    /**
     * Generates a random string of characters
     * @param min  the minimum length of the string(inclusive)
     * @param max  the maximum length of the string(exclusive)
     * @return  returns a random string
     */
    public static String randomString(int min,int max){
        int lengthOfString = randomNumber(min, max);
        StringBuilder string = new StringBuilder();
        for(int i=0;i<lengthOfString;i++){
            string.append(randomCharacter());
        }
        return string.toString();
    }
}
