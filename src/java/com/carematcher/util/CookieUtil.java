/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.util;

import java.security.SecureRandom;
import java.util.EnumMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pooja Purohit
 */
public class CookieUtil {
    
    public static enum CookieType {
        SESSIONID,
        LOGIN
    }
    
    private static final Map<CookieType, String> cookieMap = new EnumMap<CookieType, String>(CookieType.class);
    static {
        cookieMap.put(CookieType.SESSIONID, "sessionidCookie");
        cookieMap.put(CookieType.LOGIN, "loginCookie");
    }
    
    /** Gets a cookie based on the cookie name
     * 
     * @param request the HttpServletRequest
     * @param cookieName the name of the cookie to find
     * @return the found cookie, or null if not existing
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(cookieName)) {
                    cookie = c;
                    break;
                }
            }
        }
        
        return cookie;
    }
    
    /** Get the cookie for the specified standard cookie type
     * 
     * @param request the HttpServletRequest
     * @param type the enum value of the standard cookie type
     * @return 
     */
    public static Cookie getCookie(HttpServletRequest request, CookieType type) {
        if (cookieMap.containsKey(type)) {
            return getCookie(request, cookieMap.get(type));
        }
        else return null;
    }
    
    public static void replaceCookie(HttpServletRequest request, 
                                     HttpServletResponse response, 
                                     CookieType type,
                                     String newValue) 
    {
        if (cookieMap.containsKey(type)) {
            replaceCookie(request, response, cookieMap.get(type), newValue);
        }
    }
    
    public static void replaceCookie(HttpServletRequest request, 
                                     HttpServletResponse response,
                                     String cookieName, 
                                     String newValue) 
    {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie != null) {
            cookie.setValue(newValue);
            response.addCookie(cookie);
        }
    }
    
    public static Cookie createLoginCookie(String username) {
        SecureRandom rand = new SecureRandom();
        rand.setSeed(System.currentTimeMillis());
        String series = String.valueOf(rand.nextLong());
        String token = String.valueOf(rand.nextLong());
        String cookieVal = username + "; " + series + "; " + token;
        return new Cookie("loginCookie", cookieVal);
    }
}
