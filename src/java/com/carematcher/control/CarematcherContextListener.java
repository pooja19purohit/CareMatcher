/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.Role;
import com.carematcher.business.User;
import com.carematcher.business.UserRole;
import com.carematcher.data.UserDB;
import com.carematcher.data.UserRoleDB;
import com.carematcher.search.LuceneSearch;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author kbuck
 */
public class CarematcherContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        //  Get the servlet context
        ServletContext sc = sce.getServletContext();
        
        //  Create a counter for the number of sessions
        int total_session_count = 0;
        sc.setAttribute("total_session_count", total_session_count);
        int active_session_count = 0;
        sc.setAttribute("active_session_count", active_session_count);
        
        //  Initialize the current year for copyright notices
        GregorianCalendar gc = new GregorianCalendar();
        int currentYear = gc.get(GregorianCalendar.YEAR);
        sc.setAttribute("currentYear", currentYear);
        
        //  Set Lucene initial parameters from the web.xml file
        String index = sc.getRealPath(sc.getInitParameter("indexPath"));
        String data = sc.getRealPath(sc.getInitParameter("dataPath"));
        String url = sc.getInitParameter("dbURL");
        String user = sc.getInitParameter("dbUSER");
        String pass = sc.getInitParameter("dbPASS");
        if ("default".equals(pass)) pass = "";
        String driver = sc.getInitParameter("jdbcDriver");
        if (driver == null || driver.trim().isEmpty()) driver = "com.mysql.jdbc.Driver";
        int max_search = Integer.parseInt(sc.getInitParameter("maxSearch"));
        System.out.println("Index: " + index);
        System.out.println("Data: " + data);
        System.out.println("URL: " + url);
        System.out.println("User: " + user);
        System.out.println("Pass: " + pass);
        System.out.println("Max Search: " + max_search);
        System.out.println("Driver: " + driver);
        LuceneSearch.setIndex(index);
        LuceneSearch.setData(data);
        LuceneSearch.setURL(url);
        LuceneSearch.setUser(user);
        LuceneSearch.setPass(pass);
        LuceneSearch.setMaxSearch(max_search);
        LuceneSearch.setDriver(driver);
        
        //  Create initial index of database
        LuceneSearch.reindex();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //  Deregister the JDBC driver loaded for searching
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getName().equals("com.mysql.jdbc.Driver")) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    Logger.getLogger(CarematcherContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
