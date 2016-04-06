/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.RememberedUser;
import com.carematcher.business.User;
import com.carematcher.data.RememberedUserDB;
import com.carematcher.data.UserDB;
import com.carematcher.util.CookieUtil;
import com.carematcher.util.CookieUtil.CookieType;
import java.io.IOException;
import java.security.SecureRandom;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet designed to read the cookies for remembered users, validate them against
 * the remembered users database, then forward the username & password for a valid
 * user to the login servlet - for new or invalid cookie info, forward to login screen
 *
 * @author Kevin Buckley
 */
public class RememberedUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/index.jsp";
        Cookie loginCookie = CookieUtil.getCookie(request, CookieType.LOGIN);
        
        if (loginCookie != null) {
            //  Get the username, series & token values from the cookie
            String[] values = loginCookie.getValue().split(";");
            if (values.length == 3) {
                String username = values[0].trim();
                String series = values[1].trim();
                String token = values[2].trim();
                
                RememberedUser ru = RememberedUserDB.selectRememberedUserByUsername(username);
                //  Check if the remembered user is valid & corresponds to actual user
                if (validateRememberedUser(ru, series, token) &&
                    UserDB.emailExists(ru.getUsername())) 
                {
                    //  Check that the remembered user was updated successfully
                    if (updateRememberedUser(ru)) {
                        //  Get the actual user, update request parameters & forward to login servlet
                        User user = UserDB.selectUserByEmail(ru.getUsername());
                        request.setAttribute("username", ru.getUsername());
                        request.setAttribute("password", user.getPassword());
                        url = "/login";
                    }
                }
            }
        }
        
        //  Forward request to login servlet if remembered user found, login screen otherwise
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    private boolean validateRememberedUser(RememberedUser ru, String series, String token) {
        if (ru == null) return false;
        
        //  Validate series & token
        String ruSeries = ru.getSeries();
        String ruToken = ru.getToken();
        
        //  Check that the series & token in the cookie match the database
        return (series.equals(ruSeries) && token.equals(ruToken));
    }
    
    private boolean updateRememberedUser(RememberedUser ru) {
        String newSeries = getNewSeries(ru.getSeries());
        String newToken = getNewToken();
        if (newSeries != null && newToken != null) {
            ru.setSeries(newSeries);
            ru.setToken(newToken);
            return RememberedUserDB.update(ru);
        }
        else return false;
    }
        
    private String getNewSeries(String series) {
        long lSeries = Long.parseLong(series);
        if ((lSeries + 1) <= Long.MAX_VALUE) {
            return String.valueOf(lSeries + 1);
        } else {
            return String.valueOf(Long.MIN_VALUE);
        }
    }
    
    private String getNewToken() {
        //  Create a random token
        SecureRandom rand = new SecureRandom();
        rand.setSeed(System.currentTimeMillis());
        long newTok = rand.nextLong();
        return String.valueOf(newTok);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
