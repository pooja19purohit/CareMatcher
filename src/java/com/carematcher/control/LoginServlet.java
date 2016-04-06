/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.Customer;
import com.carematcher.business.Practice;
import com.carematcher.business.Provider;
import com.carematcher.business.User;
import com.carematcher.data.UserDB;
import com.carematcher.util.CookieUtil;
import com.carematcher.util.PasswordHash;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pooja Purohit
 */
public class LoginServlet extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        String url = "/home.jsp";
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }
        
        if (action.equals("login")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            if (email == null || password == null) {
                request.setAttribute("errorMessage", "null vals");
                //request.setAttribute("errorMessage", "Invalid Credentials. Please try Again.");
            }
            else if (!UserDB.emailExists(email)) {
                request.setAttribute("errorMessage", "email doesnt exist");
                //request.setAttribute("errorMessage", "Invalid Credentials. Please try Again.");
            }
            else {
                User user = UserDB.selectUserByEmail(email);
                try {
                    if (!PasswordHash.validatePassword(password, user.getPassword())) {
                        request.setAttribute("errorMessage", "Invalid Credentials. Please try Again.");
                    }
                    else {
                        switch(user.role()) {
                            case CUSTOMER:
                                Customer c = (Customer)user;
                                session.setAttribute("user", c);
                                break;
                            case PROVIDER:
                                Provider p = (Provider)user;
                                session.setAttribute("user", p);
                                break;
                            case PRACTICE:
                                Practice pr = (Practice)user;
                                session.setAttribute("user", pr);
                                break;
                            default:
                                session.setAttribute("user", user);
                                break;
                        }
                        request.setAttribute("profile", user);
                        url = "/profile.jsp";
                        //  If 'remember me' selected, and no login cookie exists, create one
                        if (request.getParameter("rememberMe") != null && 
                            CookieUtil.getCookie(request, CookieUtil.CookieType.LOGIN) == null)
                        {
                            response.addCookie(CookieUtil.createLoginCookie(user.getEmail()));
                        }
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
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
