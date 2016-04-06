/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

/**
 *
 * @author kbuck
 */
public enum PhoneType {
    
    HOME,
    
    OFFICE,
    
    CELL,
    
    FAX;
    
    @Override
    public String toString() {
        switch(this) {
            case HOME: return "home";
            case OFFICE: return "office";
            case CELL: return "cell";
            case FAX: return "fax";
            default: return "home";
        }
    }
    
    /** Gets the enum value of the specified string
     * 
     * @param phoneType the string representation of the enum value
     * @return the PhoneType enum for the string, HOME is default
     */
    public static PhoneType getPhoneType(String phoneType) {
        if (HOME.toString().equals(phoneType)) return HOME;
        else if (OFFICE.toString().equals(phoneType)) return OFFICE;
        else if (CELL.toString().equals(phoneType)) return CELL;
        else if (FAX.toString().equals(phoneType)) return FAX;
        else return HOME;
    }
}
