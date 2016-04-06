/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.Address;
import com.carematcher.business.Customer;
import com.carematcher.business.Phone;
import com.carematcher.business.PhoneType;
import com.carematcher.business.Practice;
import com.carematcher.business.Provider;
import com.carematcher.business.Role;
import com.carematcher.business.User;
import com.carematcher.business.UserRole;
import com.carematcher.data.UserRoleDB;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author kbuck
 */
public class RegistrationServlet extends HttpServlet {

    private static String errorMessage = null;
    
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
        
        /** Uncomment the following code to create the DB tables on first run
         * 
         */
        //_DbTablesCreate.generateTables();
        
        Map<String, String[]> paramMap = request.getParameterMap();
        String userType = paramMap.get("userType")[0];
        Role type = Role.toRole(userType);
        
        
        String url;
        
        User user = null; 
        switch (type) {
            case CUSTOMER:
                user = registerCustomer(paramMap);
                break;
            case PROVIDER:
                user = registerProvider(paramMap);
                break;
            case PRACTICE:
                user = registerPractice(paramMap);
                break;
            default:
                errorMessage = "user type not recognized";
                break;
        }
        
        if (user != null) {
            url = "/home";
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }
        else {
            url = "/registration.jsp";
            if (errorMessage == null)
                errorMessage = "There was an error in registration.  "
                    + "Please try again, and if the problem persists, " 
                    + "contact as system administrator";
            request.setAttribute("errorMessage", errorMessage);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    /**
     * 
     * @param paramMap
     * @return 
     */
    private static Customer registerCustomer(Map<String, String[]> paramMap) {
        //  Create new Customer object & set it's role
        Customer c = new Customer();
        
        //  Get User-common attributes
        if (!setCommonAttributes(c, paramMap)) return null;
        errorMessage = "past setting common attributes";
        
        //  Customer-Specific Attributes
        if (paramMap.containsKey("dateOB")) {
            String date = paramMap.get("dateOB")[0];
            try {
                Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                c.setDateOfBirth(dob);
            } catch (ParseException ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        
        UserRole role = UserRoleDB.selectUserRoleByRole(Role.CUSTOMER);
        if (role == null) return null;
        else if (!UserRoleDB.insertUserToRole(c, Role.CUSTOMER)) return null;
        else return c;
    }
    
    /**
     * 
     * @param paramMap
     * @return 
     */
    private static Provider registerProvider(Map<String, String[]> paramMap) {
        Provider p = new Provider();
        
        if (!setCommonAttributes(p, paramMap)) return null;
        
        //Provider Specific Attributes:
        boolean acceptingNewPatients = false;
        boolean willTravel = false;
        
        if (paramMap.containsKey("accepting")) {
            acceptingNewPatients = true;
        }
        p.setAcceptingNewPatients(acceptingNewPatients);
        
        if (paramMap.containsKey("willTravel")) {
            willTravel = true;
        }
        p.setWillTravel(willTravel);
        
        if (paramMap.containsKey("experience")) {
            int years;
            try {
                years = Integer.parseInt(paramMap.get("experience")[0]);
            } catch(NumberFormatException e) {
                years = 0;
            }
            
            p.setYearsPracticing(years);
        }
        
        UserRole role = UserRoleDB.selectUserRoleByRole(Role.PROVIDER);
        if (role == null) return null;
        else if (!UserRoleDB.insertUserToRole(p, Role.PROVIDER)) return null;
        else return p;
    }
    
    private static Practice registerPractice(Map<String, String[]> paramMap) {
        Practice p = new Practice();
        if (!UserRoleDB.roleExists(Role.PRACTICE)) {
            UserRole role = new UserRole(Role.PRACTICE);
            UserRoleDB.insertRole(role);
        }
        p.setUserRole(UserRoleDB.selectUserRoleByRole(Role.PRACTICE));
        
        if (!setCommonAttributes(p, paramMap)) return null;
        
        UserRole role = UserRoleDB.selectUserRoleByRole(Role.PRACTICE);
        if (role == null) return null;
        else if (!UserRoleDB.insertUserToRole(p, Role.PRACTICE)) return null;
        else return p;
    }
    
    /**
     * 
     * @param user
     * @param paramMap
     * @return 
     */
    private static boolean setCommonAttributes(User user, Map<String, String[]> paramMap) {
        //  Common Registration Requirements:
        String firstName;
        String lastName;
        String midInit;
        String email;
        String password;
        
        //  Check that values are defined & assign to local vars
        if (!paramMap.containsKey("firstName")) return false;
        else {
            firstName = paramMap.get("firstName")[0].trim();
        }
        
        String[] lastNames = paramMap.get("lastName");
        if (lastNames == null || lastNames[0].trim().isEmpty()) lastName = firstName;
        else lastName = lastNames[0];
        
        String[] midInits = paramMap.get("midInit");
        if (midInits == null || midInits[0].trim().isEmpty()) midInit = "_";
        else midInit = midInits[0];
        
        if (!paramMap.containsKey("email")) return false;
        else {
            email = paramMap.get("email")[0].trim();
        }
        
        if (!paramMap.containsKey("password")) return false;
        else {
            password = paramMap.get("password")[0];
        }
        
        Address address = getAddress(paramMap);
        if (address == null) return false;
        
        Phone phone = getPhone(paramMap);
        if (phone == null) return false;
            
        //  Image
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMidInit(midInit);
        user.setEmail(email);
        user.setPassword(password); //  Hashing is performed in DAO
        user.addAddress(address);
        user.addPhone(phone);
        
        return true;
    }
    
    /** Gets the address from the request parameter map, & creates object
     * 
     * @param paramMap the httpRequest parameter map
     * @return an Address Object, or null if not all of the required fields are completed
     */
    private static Address getAddress(Map<String, String[]> paramMap) {
        Address address = new Address();
        String street;
        String city;
        String state;
        String zip;
        if (!paramMap.containsKey("address1")) return null;
        else {
            street = paramMap.get("address1")[0];
        }
        
        if (paramMap.containsKey("address2")) {
            String street2 = paramMap.get("address2")[0];
            if (street2 != null && !street2.trim().isEmpty())
                street = street.concat(", " + paramMap.get("address2")[0]);
        }
        
        if (!paramMap.containsKey("city")) return null;
        else {
            city = paramMap.get("city")[0];
        }
        
        if (!paramMap.containsKey("state")) return null;
        else {
            state = paramMap.get("state")[0];
        }
        
        if (!paramMap.containsKey("zip")) return null;
        else {
            zip = paramMap.get("zip")[0];
        }
        
        address.setStreet(street);
        address.setCity(city);
        address.setSt(state);
        address.setZip(zip);
        address.setPrimary(true);
        
        return address;
    }
    
    /** Get a phone object from the request parameters
     * 
     * @param paramMap
     * @return 
     */
    private static Phone getPhone(Map<String, String[]> paramMap) {
        Phone phone = new Phone();
        String phoneNumber;
        PhoneType phoneType;
        if (!paramMap.containsKey("phoneNum")) return null;
        else {
            phoneNumber = paramMap.get("phoneNum")[0];
        }
        if (!paramMap.containsKey("phoneType")) return null;
        else {
            phoneType = PhoneType.getPhoneType(paramMap.get("phoneType")[0]);
        }
        
        phone.setPhoneNumber(phoneNumber);
        phone.setPhoneType(phoneType);
        
        return phone;
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
