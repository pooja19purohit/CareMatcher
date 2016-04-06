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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pooja Purohit
 */
//@WebServlet(name = "LocationUpdateServlet", urlPatterns = {"/LocationUpdateServlet"})
public class LocationUpdateServlet extends HttpServlet {

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
        
        String email = request.getParameter("userEmail");
        String str_address = request.getParameter("address");
        String str_long = request.getParameter("longitude");
        String str_lat = request.getParameter("latitude");
        Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, email);
        Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, str_address);
        Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, str_long);
        Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, str_lat);
        
        if (email != null && str_address != null && str_long != null && str_lat != null) {
            int timeout = 0; 
            User user = null;
            while (timeout < 5) {   //  try to get user maximum of 5 times
                user = UserDB.selectUserByEmail(email);
                //  If the user was found, break out of the loop
                if (user != null) { 
                    Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, "Found user");
                    break; 
                }
                //  otherwise, wait 1 second and try again, timing out after 5 trys
                else {
                    timeout++;
                    try { Thread.sleep(1000); }
                    catch (Exception e) { }
                }
            }
            //  If the user is still undefined, return
            if (user == null) {
                Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, "No user found");
                return;
            }
            
            //  Get this address of the user
            Address address = null;
            for (Address add : user.getAddresses()) {
                String[] parts = str_address.split(", ");
                if (parts.length > 2) {
                    if (add.getStreet().startsWith(parts[0]) &&
                        add.getCity().equals(parts[1]) &&
                        add.getSt().equals(parts[2])) {
                        address = add;
                        break;
                    }
                }
            }
            
            if (address == null) {
                Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, "No address found in update");
                return;
            }
            else { 
                Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, "Found address");
            }
            
            //  Parse the longitude & latitude
            double latitude;
            double longitude;
            try {
                latitude = Double.parseDouble(str_lat);
                longitude = Double.parseDouble(str_long);
            } catch (NumberFormatException e) {
               Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, e);
               return;
            }
            
            address.setLatitude(latitude);
            address.setLongitude(longitude);
            
            if(AddressDB.update(address)) Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, "updated");
            else Logger.getLogger(LocationUpdateServlet.class.getName()).log(Level.SEVERE, "failed address update");
        }
        
//        //  Set the user for the session
//        User user = UserDB.selectUserByEmail(request.getParameter("userEmail"));
//        HttpSession session = request.getSession();
//        //User user = (User) session.getAttribute("user");
//        
//        //  Check that the user is not null
//        if (user != null) {
//            //  Get the request parameters
//            double latitude = Double.parseDouble(request.getParameter("latitude"));
//            double longitude = Double.parseDouble(request.getParameter("longitude"));
//            System.out.println(latitude + "," + longitude);
//
//            User u = UserDB.selectUserByEmail(user.getEmail());
//            u.setLatitude(latitude);
//            u.setLongitude(longitude);
//            UserDB.updateUser(u);
//            User uu = UserDB.selectUserByEmail(user.getEmail());
//            System.out.println(uu.getLatitude() + "," + uu.getLongitude());
//            session.setAttribute("user", u);    //  Update the session user
//        }
//        else {
//            
//            System.out.println("User not found in the session");
//        }
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
