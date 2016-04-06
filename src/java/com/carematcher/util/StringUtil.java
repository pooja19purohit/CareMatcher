/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.util;

/**
 *
 * @author kbuck
 */
public class StringUtil {
    
    public static String capitalize(String string) {
        if (string == null || string.trim().isEmpty()) return null;
        
        String[] toks = string.split(" ");  //  Split on whitespace
        if (toks.length != 1) {
            StringBuilder sb = new StringBuilder();
            for (String tok : toks) {
                if (tok.length() < 2) sb.append(tok.toUpperCase());
                else {
                    sb.append(Character.toUpperCase(tok.charAt(0)));
                    sb.append(tok.substring(1));
                }
                sb.append(" ");
            }
            return sb.toString();
        }
        else {
            if (string.length() < 2) return string.toUpperCase();
            else {
                return Character.toUpperCase(string.charAt(0)) + string.substring(1);
            }
        }
    }
    
}
