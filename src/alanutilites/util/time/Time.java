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
package alanutilites.util.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Time {
    private Calendar calendar;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
    private final SimpleDateFormat TIME_STAMP = new SimpleDateFormat("yyyyMMddkkmmss");
    
    /**
     * Creates a calendar with an initial date
     * @param year  a year
     * @param month  a month from 1-12
     * @param day  a day 
     * @param hours  hours 24 hours
     * @param minutes  minutes
     * @param seconds  seconds
     */
    public Time(int year,int month,int day,int hours,int minutes,int seconds){
        calendar = Calendar.getInstance();
        calendar.set(year, month-1, day, hours, minutes, seconds);
    }
    
    /**
     * Creates a time object from a timestamp
     * @param timeStamp  the timeStamp
     */
    public Time(String timeStamp){
        String newTimeStamp = new StringBuilder(timeStamp).reverse().toString();
        int seconds = Integer.parseInt(new StringBuilder(newTimeStamp.substring(0,2)).reverse().toString());
        int minutes = Integer.parseInt(new StringBuilder(newTimeStamp.substring(2,4)).reverse().toString());
        int hours = Integer.parseInt(new StringBuilder(newTimeStamp.substring(4,6)).reverse().toString());
        int days = Integer.parseInt(new StringBuilder(newTimeStamp.substring(6,8)).reverse().toString());
        int months = Integer.parseInt(new StringBuilder(newTimeStamp.substring(8,10)).reverse().toString());
        int years = Integer.parseInt(new StringBuilder(newTimeStamp.substring(10,newTimeStamp.length())).reverse().toString());
        calendar = Calendar.getInstance();
        calendar.set(years, months-1, days, hours, minutes, seconds);
    }
    
    private Time(Calendar calendar){
        this.calendar = calendar;
    }
    
    /**
     * Gets the current time
     * @return  returns a new time
     */
    public static Time getCurrentTime(){
        return new Time(Calendar.getInstance());
    }
        
    /**
     * Compares this time to another time
     * if this time is before the other time than returns -1
     * if this time is after the other time than returns 1
     * if this time is exactly the same as the other time return 1
     * @param time  the other time to compare
     * @return  return 
     * -1 - before, 
     *  0 - exact,
     *  1 - after
     */
    public int compare(Time time){
        if(getTimeStamp() > time.getTimeStamp()){
            return 1;
        }
        else if(getTimeStamp() < time.getTimeStamp()){
            return -1;
        }
        else{
            return 0;
        }
    }
    
    /**
     * Compares a time to another time by minute not be seconds
     * @param time  the other time you want compare
     * @return  if the 2 times are equals returns true else returns false
     */
    public boolean estimateCompare(Time time){
        return getYear() == time.getYear() &&
                getMonth() == time.getMonth() && 
                getDay() == time.getDay() &&
                get24Hour() == time.get24Hour() &&
                getMinute() == time.getMinute();
    }
    
    /**
     * Compares a time to another time by second
     * @param time  the other time you want compare
     * @return  returns  if the 2 times are equals returns true else returns false
     */
    public boolean exactCompare(Time time){
        return getYear() == time.getYear() &&
                getMonth() == time.getMonth() && 
                getDay() == time.getDay() &&
                get24Hour() == time.get24Hour() &&
                getMinute() == time.getMinute() &&
                getSecond() == time.getSecond();
    }
    
    /**
     * Adds years to the current time
     * @param years  the amount of years you want to add
     */
    public void addYears(int years){
        calendar.add(Calendar.YEAR, years);
    }
    
    /**
     * Adds months to the current time
     * @param months  the amount of months you want to add
     */
    public void addMonths(int months){
        calendar.add(Calendar.MONTH, months);
    }
    
    /**
     * Adds days to the current time
     * @param days  the amount of days you want to add
     */
    public void addDays(int days){
        calendar.add(Calendar.DAY_OF_YEAR, days);
    }
    
    /**
     * Adds hours to the current time
     * @param hours  the amount of hours you want to add
     */
    public void addHours(int hours){
        calendar.add(Calendar.HOUR, hours);
    }
    
    /**
     * Adds minutes to the current time
     * @param minutes  the amount of minutes you want to add
     */
    public void addMinutes(int minutes){
        calendar.add(Calendar.MINUTE, minutes);
    }
    
    /**
     * Adds seconds to the current time
     * @param seconds  the amount of seconds you want to add
     */
    public void addSeconds(int seconds){
        calendar.add(Calendar.SECOND, seconds);
    }
    
    /**
     * Gets the year
     * @return  returns year
     */
    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * Gets the month
     * @return  returns month
     */
    public int getMonth(){
        return calendar.get(Calendar.MONTH)+1;
    }
    
    /**
     * Gets the day
     * @return  returns day
     */
    public int getDay(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }  
    
    /**
     * Gets the hour in 12 hour format
     * @return  returns hour in 12 hour format
     */
    public int get12Hour(){
        return calendar.get(Calendar.HOUR);
    }
    
    /**
     * Gets the hour in 24 hour format
     * @return  returns hour in 24 hour format
     */
    public int get24Hour(){
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * Gets the minute
     * @return  returns minute
     */
    public int getMinute(){
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * Gets the second
     * @return  returns second
     */
    public int getSecond(){
        return calendar.get(Calendar.SECOND);
    }
    
    /**
     * returns the current time marker
     * EX:
     * AM
     * PM
     * @return  returns either AM or PM
     */
    public String getTimeMarker(){
        return (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
    }
    
    /**
     * A long timestamp
     * @return  20160208165623
     */
    public long getTimeStamp(){
        return Long.parseLong(TIME_STAMP.format(calendar.getTime()));
    }
    
    /**
     * turns a month number to a String month
     * @param month  the month (1-12)
     * @return  returns a nice month
     * EX:
     * 1 = January
     * 2 = February
     * 3 = March
     * etc.
     */
    public static String getNicerMonth(int month){
        String newMonth = "Error";
        switch (month) {
            case 1:
                newMonth = "January";
                break;
            case 2:
                newMonth = "February";
                break;
            case 3:
                newMonth = "March";
                break;
            case 4:
                newMonth = "April";
                break;
            case 5:
                newMonth = "May";
                break;
            case 6:
                newMonth = "June";
                break;
            case 7:
                newMonth = "July";
                break;
            case 8:
                newMonth = "August";
                break;
            case 9:
                newMonth = "September";
                break;
            case 10:
                newMonth = "October";
                break;
            case 11:
                newMonth = "November";
                break;
            case 12:
                newMonth = "December";
                break;
        }
        return newMonth;        
    }
    
    /**
     * Returns a nice looking timestamp
     * EX:
     * 20160208165623 = February 8, 2016 - 4:56:23 PM
     * @param hour12  true for 12 hour format and false for 24 hour format
     * @return 
     */
    public String nicerTimeStamp(boolean hour12){
        int hour = get24Hour();
        String timeOfDay = "";
        if(hour12){
            if(hour > 12){
                hour -= 12;
                timeOfDay = " PM";
            }
            else{
                timeOfDay = " AM";
            }
        }
        String newTimeStamp = getNicerMonth(getMonth())+" "+getDay()+", "+getYear()+" - "+hour+":"+getMinute()+":"+getSecond()+timeOfDay;
        return newTimeStamp;
        
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(DATE_FORMAT.format(calendar.getTime()));
        return builder.toString();
    }
}
