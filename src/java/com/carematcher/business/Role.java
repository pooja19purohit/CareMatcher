/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import com.carematcher.data.UserRoleDB;

/**
 *
 * @author kbuck
 */
public enum Role {
    
    /** Administrator User Role enum representation
     * 
     */
    ADMINISTRATOR,
    
    /** Customer User Role enum representation
     * 
     */
    CUSTOMER,
    
    /** Provider User Role enum representation
     * 
     */
    PROVIDER,
    
    /** Practice User Role enum representation
     * 
     */
    PRACTICE,
    
    /** Default user role with no permissions
     * 
     */
    DEFAULT;
    
    @Override
    public String toString() {
        switch(this) {
            case ADMINISTRATOR: return "administrator";
            case CUSTOMER: return "customer";
            case PROVIDER: return "provider";
            case PRACTICE: return "practice";
            case DEFAULT: return "default";
            default: return "default";
        }
    }
    
    /** Gets the ROLE enum value for the provided string that matches
     * 
     * @param role the string representation of the role
     * @return the enum role value, or DEFAULT if nothing returns
     */
    public static Role toRole(String role) {
        if (CUSTOMER.toString().equals(role)) {
            return CUSTOMER;
        }
        else if (PROVIDER.toString().equals(role)) {
            return PROVIDER;
        }
        else if (PRACTICE.toString().equals(role)) {
            return PRACTICE;
        }
        else if (ADMINISTRATOR.toString().equals(role)) {
            return ADMINISTRATOR;
        }
        else return DEFAULT;
    }
    
    public static Role toRole(int role_id) {
        UserRole ur = UserRoleDB.selectUserRoleByRoleId(role_id);
        if (ur != null) return toRole(ur.roleName());
        else return DEFAULT;
    }
}
