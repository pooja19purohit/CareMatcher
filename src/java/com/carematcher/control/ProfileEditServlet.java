/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.Customer;
import com.carematcher.business.Practice;
import com.carematcher.business.Provider;
import com.carematcher.business.Role;
import com.carematcher.business.Service;
import com.carematcher.business.User;
import com.carematcher.data.CustomerDB;
import com.carematcher.data.PracticeDB;
import com.carematcher.data.ProviderDB;
import com.carematcher.data.ServiceDB;
import com.carematcher.data.UserDB;
import com.carematcher.search.LuceneSearch;
import com.carematcher.search.ResultKey;
import com.carematcher.search.SearchType;
import com.carematcher.util.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Map;
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
public class ProfileEditServlet extends HttpServlet {

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
        
        //  Get the user for the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        //  Check that the user is not null
        if (user != null) {
            //  Get the request parameters
            String service = request.getParameter("service");
            //  Check if the service is linking or unlinking the user
            if ("link-user".equals(service) || "unlink-user".equals(service)) {
                //  Get the link direction (link vs unlink)
                boolean link = "link-user".equals(service);
                String email = request.getParameter("userid");
                User linking = UserDB.selectUserByEmail(email);
                user = linkUser(user, linking, link);
                
                //  On successful link, reset the session user attribute
                if (user != null) session.setAttribute("user", user);
                else request.setAttribute("errorMessage", "Failed to link/unlink users.");
                
                getServletContext().getRequestDispatcher("/home").forward(request, response);
            }
            else if ("service-search".equals(service)) {
                String query = request.getParameter("query");
                LuceneSearch.allowLeadingWildcardSearch(true);
                query = "*" + query + "*";
                Map<ResultKey, Object> searchResults = LuceneSearch.search(query, SearchType.SERVICE_BY_NAME);
                LinkedHashSet<String> names = (LinkedHashSet<String>)searchResults.get(ResultKey.SERVICE);
                Logger.getLogger(ProfileEditServlet.class.getName()).log(Level.SEVERE, Integer.toString(names.size()));
                StringBuilder sb = new StringBuilder();
                for (String name : names) {
                    Logger.getLogger(ProfileEditServlet.class.getName()).log(Level.SEVERE, name);
                    sb.append(name);
                    sb.append(":");
                }
                PrintWriter out = response.getWriter();
                out.write(sb.toString());
                out.flush();
                out.close();
            }
            else if ("bio-update".equals(service)) {
                String newBioText = request.getParameter("bio");

                //  If updating the Biography
                if (newBioText != null && !newBioText.trim().isEmpty()) {
                    User u = UserDB.selectUserByEmail(user.getEmail());
                    u.setBiography(newBioText);
                    UserDB.updateUser(u);
                    session.setAttribute("user", u);    //  Update the session user
                }
            }
            else if("add-services".equals(service)) {
                String serviceName = request.getParameter("name");
           
                //  Get the service, adding it to the DB if not found
                Service serv = ServiceDB.selectServiceByName(serviceName);
                if (serv == null) {
                    serv = new Service();
                    serviceName = StringUtil.capitalize(serviceName);
                    serv.setName(serviceName);
                    ServiceDB.insert(serv);
                }
                if (user.role() == Role.PROVIDER) {
                    Provider p = ProviderDB.selectProviderByEmail(user.getEmail());
                    p.addServicePerformed(serv);
                    ProviderDB.update(p);
                    session.setAttribute("user", p);
                } 
                else if (user.role() == Role.PRACTICE) {
                    Practice p = PracticeDB.selectPracticeByEmail(user.getEmail());
                    p.addService(serv);
                    PracticeDB.update(p);
                    session.setAttribute("user", p);
                }
                
            }
            else if ("description-edit".equals(service)) {
                String name = request.getParameter("name");
                String descrip = request.getParameter("descrip");
                if (name == null || descrip == null) return;
                
                Service serv = ServiceDB.selectServiceByName(name);
                if (serv == null) return;
                
                serv.setDescription(descrip);
                ServiceDB.update(serv);
            }
        }
    }
    
    private User linkUser(User target, User linked, boolean link) {
        if (target.role().equals(Role.CUSTOMER)) {
            Customer customer = CustomerDB.selectCustomerByEmail(target.getEmail());
            switch(linked.role()) {
                case PROVIDER:
                    Provider p = (Provider) linked;
                    //  Check if linking or unlinking
                    if (link) customer.addLinkedProvider(p);
                    else customer.removeLinkedProvider(p);
                    //  Return the updated customer if update successful
                    if (CustomerDB.update(customer)) return customer;
                    else return null;
                default:
                    return null;
            }
        }
        else return null;
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
