/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.Customer;
import com.carematcher.business.User;
import com.carematcher.data.CustomerDB;
import com.carematcher.data.UserDB;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kbuck
 */
public class AppointmentServlet extends HttpServlet {
    
    private enum InfoType {
        F_NAME,
        M_INIT,
        L_NAME,
        EMAIL,
        PREF_DATE,
        PREF_TIME,
        WEEKEND,
        WEEKDAY,
        MORNING,
        AFTERNOON,
        EVENING,
        COMMENT,
        FOR_ME
    }
    
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
        
        String url;
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            url = "/home";
        }
        else {
            User user = (User) session.getAttribute("user");
            if (user == null) url = "/logout";  //  Error: session active but no user, logout
            else {
                Map<String, String> paramMap = getParams(request, user);
                request.setAttribute("infoMap", paramMap);
                url = "/confirmation.jsp";
            }
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
    private Map<String, String> getParams(HttpServletRequest request, User user) {
        Map<String, String> confirmMap = new HashMap<String, String>();
        String email = request.getParameter("email");
        String fname = request.getParameter("firstName");
        String lname = request.getParameter("lastName");
        String mi = request.getParameter("midInit");
        String prefDate = request.getParameter("prefDate");
        String prefTime = request.getParameter("prefTime");
        String weekends = request.getParameter("wkend");
        String weekdays = request.getParameter("wkday");
        String mornings = request.getParameter("morn");
        String afternoons = request.getParameter("after");
        String evenings = request.getParameter("even");
        String comments = request.getParameter("comments");
        String forme = request.getParameter("for-me");
        
        if (prefDate == null) prefDate = "No Preferred Date.";
        if (prefTime == null) prefTime = "No Preferred Time.";
        if (comments == null) comments = "No additional comments.";
        if (mi == null) mi = "";
        weekends = (weekends != null ? "YES" : "NO");
        weekdays = (weekdays != null ? "YES" : "NO");
        mornings = (mornings != null ? "YES" : "NO");
        afternoons = (afternoons != null ? "YES" : "NO");
        evenings = (evenings != null ? "YES" : "NO");
        
        confirmMap.put(InfoType.FOR_ME.name(), forme);
        confirmMap.put(InfoType.PREF_DATE.name(), prefDate);
        confirmMap.put(InfoType.PREF_TIME.name(), prefTime);
        confirmMap.put(InfoType.WEEKEND.name(), weekends);
        confirmMap.put(InfoType.WEEKDAY.name(), weekdays);
        confirmMap.put(InfoType.MORNING.name(), mornings);
        confirmMap.put(InfoType.AFTERNOON.name(), afternoons);
        confirmMap.put(InfoType.EVENING.name(), evenings);
        confirmMap.put(InfoType.COMMENT.name(), comments);
        confirmMap.put(InfoType.F_NAME.name(), fname);
        confirmMap.put(InfoType.L_NAME.name(), lname);
        confirmMap.put(InfoType.EMAIL.name(), email);
        confirmMap.put(InfoType.M_INIT.name(), mi);
        
        return confirmMap;
    }
    
    private int userCheck(String email, String fname, String lname, String mi) {
        //  First check if the specified email exists
        Customer c = CustomerDB.selectCustomerByEmail(email);
        if (c != null) {
            //  Check wether customer info is for self or another patient (family member)
            if (c.getFirstName().equals(fname.trim()) &&
                c.getLastName().equals(lname.trim())) {
                if (c.getMidInit() == null) return 0;
                else if (c.getMidInit().equals(mi.trim())) return 0;
                else return 1;
            }
            //  If customer info doesn't match entered info, return 1
            else return 1;
        }
        //  if customer doesn't exist, return -1
        else return -1;
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
