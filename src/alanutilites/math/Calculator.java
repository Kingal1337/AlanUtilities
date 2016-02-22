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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Alan Tsui
 * @version 1.0
 * @since 1.1
 */
public class Calculator {
    private static final String ALL_OPERATIONS = "+-*/%^()";
    
    private static final String NON_FOREIGN_CHARACTERS = "1234567890.+-*/%^()E ";

    private static enum OPERATION {
        ADD, SUBTRACT, MULTIPLY, DIVIVDE, MOD, POW, OPEN_PARENTHESIS, CLOSED_PARENTHESIS, NUMBER
    }
    
    /**
     * Do not let anyone instantiate this class.
     */
    private Calculator(){}
    
    /**
     * Evaluates an equation using PEMDAS
     * 
     * @param equation  the equation
     * The equation can only contain [numbers,+,-,*,/,%,^,(,), ]
     * % means mod not percentage
     * EX:
     * 1+2-3*4/5%6^( 7 )
     * @return  returns the value of the equation if there are no errors
     */
    public static CalculatorResult evaluate(String equation){
        if(!containsForeignCharacters(equation)){
            return new CalculatorResult("Foreign Characters",0);
        }
        String[] valuesAndOperators = format(equation);
        return valuesAndOperators == null ? new CalculatorResult("Error", 0) : evaluate(valuesAndOperators);
    }
    
    private static String[] format(String equation){
        int j = 0;
        for (int i = 0; i < equation.length(); ++i) {
            
            if (ALL_OPERATIONS.indexOf(equation.charAt(i)) != -1) {
                if (equation.charAt(i) == '(') {
                    ++j;
                }
                if (equation.charAt(i) == ')') {
                    --j;
                }
                if (j < 0) {
                    return null;
                }
                if ((i > 0) && (equation.charAt(i - 1) != ' ')) {
                    equation = equation.substring(0, i) + " " + equation.substring(i);
                    ++i;
                }
                if ((i < equation.length() - 1) && (equation.charAt(i + 1) != ' ')) {
                    equation = equation.substring(0, i + 1) + " " + equation.substring(i + 1);
                    ++i;
                }
            }
        }
        if (j > 0) {
            return null;
        }
        String[] equationArray = equation.split(" ");
        ArrayList<String> values = new ArrayList<>();
        
        if(equationArray.length > 1){
            String orig = null;
            String regex = "[-+/%*^()]+";
            String first = equationArray[0];
            values.add(first);
            String secound = equationArray[1];

            if(first.equals("-")){
                values.remove(0);
                values.add(first+secound);
            }

            for (int i = 0; i < equationArray.length; i++) {
                String a = equationArray[i];
                if (i >= 1){
                    String b = equationArray[i-1];
                    if(b.endsWith("E") && a.equals("-")){
                        values.remove(values.size()-1);
                        values.add(b+a+equationArray[i+1]);
                        equationArray[i+1] = "DONOTUSE";
                    }
                    else{
                        if(!a.equals("DONOTUSE")){
                            if(a.equals("(") && b.matches("[0-9]+")){
                                values.add("*");
                            }
                            if(b.matches(regex) && a.matches("[-+]+")){
                                orig = a;
                            }
                            else if(orig != null && orig.equals("-")){
                                values.add(orig + a);
                                orig = null;
                            }
                            else{
                                values.add(a);
                            }
                        }
                    }
                }
            }

            if(first.equals("+")){
                values.remove(0);
            }
            if(first.equals("-")){
                values.remove(1);
            }
            return values.toArray(new String[values.size()]);
        }
        return equationArray;
    }
        
    
    private static boolean containsForeignCharacters(String equation){
        for(char character : equation.toCharArray()){
            if(NON_FOREIGN_CHARACTERS.indexOf(character) == -1){
                return false;
            }
        }
        return true;
    }
    
    private static boolean isOperationNotPar(String character){
        int number = ALL_OPERATIONS.indexOf(character);
        return !(number == -1 || number == 6 || number == 7);
    }    
    
    private static OPERATION getOperation(int op){
        if(op == 0){
            return OPERATION.ADD;
        }
        else if(op == 1){
            return OPERATION.SUBTRACT;
        }
        else if(op == 2){
            return OPERATION.MULTIPLY;
        }
        else if(op == 3){
            return OPERATION.DIVIVDE;
        }
        else if(op == 4){
            return OPERATION.MOD;
        }
        else if(op == 5){
            return OPERATION.POW;
        }
        else if(op == 6){
            return OPERATION.OPEN_PARENTHESIS;
        }
        else if(op == 7){
            return OPERATION.CLOSED_PARENTHESIS;
        }
        else{            
            return OPERATION.NUMBER;
        }
    }
    
    private static CalculatorResult evaluate(String[] values){
        values = shuntingYard(values);
        
            System.out.println(Arrays.toString(values));
        String postFix = postFix(values);
            System.out.println(postFix);
        return !postFix.equals("ERROR") ? new CalculatorResult("Success", Double.parseDouble(postFix)) : new CalculatorResult("Error", 0);
    }
    
    private static String postFix(String[] values){
        try{
            ArrayDeque<String> stack = new ArrayDeque<>();
            for(String value : values){
                if(isOperationNotPar(value)){
                    int index = ALL_OPERATIONS.indexOf(value);
                    OPERATION op = getOperation(index);
                    double number2 = Double.parseDouble(stack.pop());
                    double number1 = Double.parseDouble(stack.pop());
                    stack.push(doOperation(number1, number2, op)+"");
                }
                else{
                    stack.push(value);
                }
            }
            return stack.size() != 1 ? "ERROR" : stack.pop();
        }catch(Exception e){
            return "ERROR";
        }
    }
    
    private static String[] shuntingYard(String[] values){
        ArrayList<String> out = new ArrayList<>();
        ArrayDeque<String> stack = new ArrayDeque<>();
        for(String currentValue : values){
            if(isOperationNotPar(currentValue)){
                while(!stack.isEmpty() && isOperationNotPar(stack.peek())){
                    if(
                            (!currentValue.equals("^") && (getPriority(currentValue) - getPriority(stack.peek())) <= 0)
                            || 
                            (currentValue.equals("^") && (getPriority(currentValue) - getPriority(stack.peek())) < 0)
                    ){
                        out.add(stack.pop());
                    }
                    else{
                        break;
                    }
                }
                stack.push(currentValue);
            } 
            else if (currentValue.equals("(")) {
                stack.push(currentValue);
            } 
            else if (currentValue.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
            } 
            else {
                out.add(currentValue);
            }
        }
        while (!stack.isEmpty()) {
            out.add(stack.pop());
        }
        String[] output = new String[out.size()];
        return out.toArray(output);
    }
    
    private static double doOperation(double firstNumber, double secondNumber, OPERATION operation){
        if(operation == OPERATION.ADD){
            return firstNumber + secondNumber;
        }
        else if(operation == OPERATION.SUBTRACT){
            return firstNumber - secondNumber;
        }
        else if(operation == OPERATION.MULTIPLY){
            return firstNumber * secondNumber;
        }
        else if(operation == OPERATION.DIVIVDE){
            return firstNumber / secondNumber;
        }
        else if(operation == OPERATION.POW){
            return Math.pow(firstNumber, secondNumber);
        }
        else if(operation == OPERATION.MOD){
            return firstNumber % secondNumber;
        }
        else{
            return 0;
        }
    }
    
    private static int getPriority(String op) {
        if(op.equals("+") || op.equals("-")){
            return 1;
        }
        else if(op.equals("*") || op.equals("/") || op.equals("%")){
            return 2;
        }
        else if(op.equals("^")){
            return 3;
        }
        return 0;
    }
    
    public static class CalculatorResult{
        public String text;
        public double answer;
        public CalculatorResult(String text, double answer){
            this.text = text;
            this.answer = answer;
        }
        
        @Override
        public String toString(){
            return text+" "+answer;
        }
        
    }
    
}
