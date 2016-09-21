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

import alanutilites.util.Arrays;
import java.awt.Color;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Random {
    private static final String MAX_LONG_LENGTH = "000000000000000000";
    private static java.util.Random random = new java.util.Random();
    
    /**
     * Do not let anyone instantiate this class.
     */
    private Random(){}
    
    /**
     * Generates a random Long of a certain size
     * 
     * Max length of the number is 9
     * 
     * Note : the parameter number does not mean anything only the length of the number is used
     * EX : 
     * randomSizeNumber("00000") - this will generate a 5 digit number
     * randomSizeNumber("ABCDE") - this will also generate a 5 digit number
     * randomSizeNumber("5123451") - this will generate a 7 digit number
     * @param number  the length of the number
     * @return  returns a random Long of a certain length; if length is greater than 9 then returns a Long with a length of 9
     */
    public static long randomSizeNumber(String number){
        number = number.length() >= MAX_LONG_LENGTH.length() ? MAX_LONG_LENGTH : number;
        String string = "";
        for(int i=0;i<number.length();i++){
            string += randomNumber(9);
        }
        return Long.parseLong(string);
    }
    
    /**
     * Gets a random number from a 0 to a maximum number(inclusive)
     * @param max  the maximum number (inclusive)
     * @return  returns a random number from 0 to maximum
     */    
    public static int randomNumber(int max){
        return randomNumber(0, max);
    }
    
    /**
     * Gets a random number from a 0 to a maximum number(inclusive)
     * @param max  the maximum number (inclusive)
     * @return  returns a random number from 0 to maximum
     */
    public static long randomNumber(long max){
        return randomNumber(0, max);
    }    
    
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
     * Generates a random letter from the alphabet (lowercase and uppercase letters)
     * @return  returns a random letter
     */
    public static char randomLetter(){
        return randomCharacter(Arrays.combine(Arrays.LOWER_CASE_LETTERS,Arrays.UPPER_CASE_LETTERS));
    }
    
    /**
     * Generates a string from a string of characters
     * @param string  the character set
     * @return  returns a random letter from the string of characters
     */
    public static char randomCharacter(String string){
        return randomCharacter(string.toCharArray());
    }
    
    /**
     * Generates a string from an array of characters
     * @param characters  the character set
     * @return  returns a random letter from the array of characters
     */
    public static char randomCharacter(char[] characters){
        return characters[randomNumber(characters.length-1)];
    }
    
    /**
     * Generates a random character
     * @return  returns a random character
     */
    public static char randomCharacter(){
        return randomCharacter(Arrays.combine(Arrays.combine(Arrays.LOWER_CASE_LETTERS,Arrays.UPPER_CASE_LETTERS),Arrays.PUNCTUATION));
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
    
    /**
     * Generates a random string at a set size from a string of character
     * EX:
     * <code>
     * string - "abcdef123"
     * size - 5
     * random string - bd3c1
     * </code>
     * @param string  the character set
     * @param size  the size of the random string
     * @return  returns a random string 
     */
    public static String randomString(String string, int size){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<size;i++){
            builder.append(randomCharacter(string));
        }
        return builder.toString();
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
}
