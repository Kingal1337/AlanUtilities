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
package alanutilites.math;

import alanutilites.util.Arrays;

/**
 * 
 * @author Alan T
 * @version 1.0
 * @since 1.1
 */

public class MathUtil {
    public static final double INFINITY = 1.0/0.0;
    public static final double NAN = 0.0 / 0.0;
    public static final double E = 2.7182818284590452354;
    public static final double PI = 3.14159265358979323846;    
    
    /**
     * Evaluates an equation using PEMDAS
     * 
     * @param equation  the equation
     * The equation can only contain [numbers,+,-,*,/,%,^,(,), ]
     * % means mod not percentage
     * EX:
     * 1+2-3*4/5%6^( 7 ) = 0.6000000000000001
     * @return  returns the value of the equation if there are no errors
     */
    public static double evaluateEquation(String equation){
        return Calculator.evaluate(equation).answer;
    }
    
    /**
     * Gets the mean/average of an array of numbers
     * @param array  an array of numbers
     * @return  returns the mean/average of an array of numbers
     */
    public static double getMean(double[] array){
        double numbersCombined = 0;
        for(double number : array){
            numbersCombined+=number;
        }
        return numbersCombined/array.length;
    }
    
    /**
     * Does the quadratic formula 
     * @param a  a variable
     * @param b  b variable
     * @param c  c variable
     * @return  returns the roots
     */
    public static double[] quadForm(double a, double b, double c){
        double x1 = ((-b)+(sqrt((Math.pow(b,2))-(4*a*c))))/(2*a);
        double x2 = ((-b)-(sqrt((Math.pow(b,2))-(4*a*c))))/(2*a);
        return x1 == x2 ? new double[]{x1} : new double[]{x1,x2};
    }
    
    /**
     * Gets the square root of a number
     * @param number  a number
     * @return  returns the square root of the number
     */
    public static double sqrt(double number){
        if(number < 0){
            return NAN;
        }
        else if(number == 0){
            return 0;
        }
        double tempNumber;
        double squareRoot = number / 2;
        do {
            tempNumber = squareRoot;
            squareRoot = (tempNumber + (number / tempNumber)) / 2;
        } while ((tempNumber - squareRoot) != 0);
        return tempNumber;
    }
    
    /**
     * Gets the biggest number from an array
     * @param array  an array
     * @return  returns the biggest number from an array. if array is empty returns 0
     */
    public static double getMax(double[] array){
        if(Arrays.isArrayEmpty(array)){
            return 0;
        }
        double maxNumber = array[0];
        for(double number : array){
            maxNumber = getMax(maxNumber,number);
        }
        return maxNumber;
    }
    
    /**
     * Gets the smallest number from an array
     * @param array  an array
     * @return  returns the smallest number from an array. if array is empty returns 0
     */
    public static double getMin(double[] array){
        if(Arrays.isArrayEmpty(array)){
            return 0;
        }
        double minNumber = array[0];
        for(double number : array){
            minNumber = getMin(minNumber,number);
        }
        return minNumber;
    }
    
    /**
     * Gets the biggest number from the two numbers
     * @param a  a number to compare
     * @param b  another number to compare
     * @return  returns the biggest number from the two numbers
     */
    public static double getMax(double a, double b){
        return a > b ? a : b;
    }
    
    /**
     * Gets the smallest number from the two numbers
     * @param a  a number to compare
     * @param b  another number to compare
     * @return  returns the smallest number from the two numbers
     */
    public static double getMin(double a, double b){
        return a < b ? a : b;
    }
    
    /**
     * Gets the absolute value of a number
     * @param a  a value
     * @return  returns the absolute value of the variable a
     */
    public static double abs(double a){
        return a <= 0.0 ? 0.0-a : a;
    }
    
    /**
     * Sample Standard Deviation is a measure that is used to quantify 
     * the amount of variation or dispersion of a set of data values.
     * A standard deviation close to 0 indicates that the data points 
     * tend to be very close to the mean (also called the expected value)
     * of the set, while a high standard deviation indicates that the data
     * points are spread out over a wider range of values. (Took from Wiki)
     * 
     * @param array  an array of values
     * @return  returns the variation of the values
     */
    public double getSampleSD(double[] array){
        double mean = getMean(array);
        double finalAnswer = 0;
        for(int i=0;i<array.length;i++){
            finalAnswer += Math.pow((array[i]-mean),2)/(array.length-1);
        }
        return sqrt(finalAnswer);
    }
    
    /**
     * Sample Standard Deviation is a measure that is used to quantify 
     * the amount of variation or dispersion of a set of data values.
     * A standard deviation close to 0 indicates that the data points 
     * tend to be very close to the mean (also called the expected value)
     * of the set, while a high standard deviation indicates that the data
     * points are spread out over a wider range of values. (Took from Wiki)
     * 
     * @param array  an array of values
     * @return  returns the variation of the values
     */
    public double getPopulationSD(double[] array){
        double mean = getMean(array);
        double finalAnswer = 0;
        for(int i=0;i<array.length;i++){
            finalAnswer += Math.pow((array[i]-mean),2)/(array.length);
        }
        return sqrt(finalAnswer);
    }
    
    /**
     * rounds a value
     * @param number  a number to round
     * @return  returns a rounded value
     */
    public static long round(double number){
        if(Number.isInteger(number+"")){
            return Long.parseLong(number+"");
        }
        else{
            String[] numbers = (number+"").split("\\.");
            long newNumber = Long.parseLong(numbers[0]);
            long decimal = Long.parseLong(numbers[1]);
            if(Long.parseLong((decimal+"").charAt(0)+"") > 4){
                newNumber ++;
            }            
            return newNumber;
        }
    }
    
    
}
