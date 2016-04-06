/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author kbuck
 */
public class CarematcherSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        
        GregorianCalendar today = new GregorianCalendar();
        session.setAttribute("year", today.get(GregorianCalendar.YEAR));
        
        //  Increment the total session count & active session count
        ServletContext sc = session.getServletContext();
        int total = (Integer)sc.getAttribute("total_session_count");
        int active = (Integer)sc.getAttribute("active_session_count");
        
        sc.setAttribute("total_session_count", ++total);
        sc.setAttribute("active_session_count", ++active);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //  Decrement the active session count
        HttpSession session = se.getSession();
        ServletContext sc = session.getServletContext();
        int active = (Integer)sc.getAttribute("active_session_count");
        sc.setAttribute("active_session_count", --active);
    }
}
