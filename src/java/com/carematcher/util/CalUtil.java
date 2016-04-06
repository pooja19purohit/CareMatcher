/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.util;

import java.util.GregorianCalendar;

/**
 *
 * @author kbuck
 */
public class CalUtil extends GregorianCalendar {
    
    private boolean validDate = false;
    
    public CalUtil(int day, int month, int year, int hours, int mins, int sec) {
        checkDate(day, month, year, hours, mins, sec);
    }
    
    public CalUtil(int day, int month, int year) {
        checkDate(day, month, year, 0, 0, 0);
    }
    
    private void checkDate(int day, int month, int year, int hours, int mins, int sec) { 
        validDate = true;
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(GregorianCalendar.YEAR);
        
        if (year >= 1900 && year <= currentYear) {  // minimum year is 1900
           this.set(GregorianCalendar.YEAR, year);
        }
        else validDate = false;
        
        if (month >= 0 && month < 12) {
            this.set(GregorianCalendar.MONTH, month);
        }
        else validDate = false;
       
        if (day > 0 && day <= this.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
           this.set(GregorianCalendar.DAY_OF_MONTH, day);
        }
        else validDate = false;
        
        if (hours >= 0 && hours < 24) {
            this.set(GregorianCalendar.HOUR_OF_DAY, hours);
        }
        else validDate = false;
        
        if (mins >= 0 && mins < 60) {
            this.set(GregorianCalendar.MINUTE, mins);
        }
        else validDate = false;
        
        if (sec >= 0 && sec < 60) {
            this.set(GregorianCalendar.SECOND, sec);
        }
        else validDate = false;
    }
    
    public boolean isValidDate() {
        return validDate;
    }
    
    public int getDay() {
        return this.get(GregorianCalendar.DAY_OF_MONTH);
    }
    
    public int getMonth() {
        return this.get(GregorianCalendar.MONTH);
    }
    
    public int getYear() {
        return this.get(GregorianCalendar.YEAR);
    }
    
    public int getHour24() {
        return this.get(GregorianCalendar.HOUR_OF_DAY);
    }
    
    public int getHour12() {
        return this.get(GregorianCalendar.HOUR);
    }
    
    public int getMinute() {
        return this.get(GregorianCalendar.MINUTE);
    }
    
    public int getSecond() {
        return this.get(GregorianCalendar.SECOND);
    }
}
