package alanutilites.math;

/**
 * 
 * @author Alan T
 */
public class Number {
    private Number(){}
    
    public static boolean isInteger(String number){
        try{
            Integer.parseInt(number);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public static boolean isDouble(String number){
        try{
            Double.parseDouble(number);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public static boolean isFloat(String number){
        try{
            Float.parseFloat(number);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public static boolean isLong(String number){
        try{
            Long.parseLong(number);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public static boolean isEven(int number){
        return number % 2 == 0;
    }
    
    public static boolean isOdd(int number){
        return number % 2 != 0;
    }
}
