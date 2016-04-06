/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.Address;
import com.carematcher.business.User;
import com.carematcher.data.AddressDB;
import com.carematcher.data.UserDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kbuck
 */
public class AdministrationServlet extends HttpServlet {

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
        User sess_user = (User) session.getAttribute("user");
        String action = request.getParameter("service");
        String url = "/home";
        
        if ("go-to-admin".equals(action)) {
            System.out.println("role id: " + sess_user.getUserRole().roleName());
            List<User> users = UserDB.selectAllUsers();
            request.setAttribute("users", users);
            
            List<Address> addresses = AddressDB.selectAddresses();
            List<Address> toUpdate = new ArrayList<Address>();
            for (Address address : addresses) {
                Double lon = address.getLongitude();
                Double lat = address.getLatitude();
                if (lon.intValue() == 0 || lat.intValue() == 0) {
                    toUpdate.add(address);
                }
            }
            if (!toUpdate.isEmpty()) {
                Logger.getLogger(AdministrationServlet.class.getName()).log(Level.INFO, "found updates");
                request.setAttribute("updates", toUpdate);
            }
            
            url = "/administration.jsp";
        }
        else if ("delete-user".equals(action)) {
            String email = request.getParameter("delselect");
            User del = UserDB.selectUserByEmail(email);
            if (sess_user != null && del != null && sess_user.equals(del)) {
                session.removeAttribute("user");
                UserDB.deleteUser(del);
                url = "/logout";
            }
            else {
                if (!UserDB.deleteUser(del)) {
                    Logger.getLogger(AdministrationServlet.class.getName()).log(Level.SEVERE, "failed to delete");
                }
                List<User> users = UserDB.selectAllUsers();
                request.setAttribute("users", users);
                url = "/administration.jsp";
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
