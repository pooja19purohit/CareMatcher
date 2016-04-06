/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.control;

import com.carematcher.business.Insurance;
import com.carematcher.business.License;
import com.carematcher.business.Practice;
import com.carematcher.business.Provider;
import com.carematcher.business.Role;
import com.carematcher.business.Service;
import com.carematcher.business.User;
import com.carematcher.data.InsuranceDB;
import com.carematcher.data.LicenseDB;
import com.carematcher.data.ProviderDB;
import com.carematcher.data.ServiceDB;
import com.carematcher.data.UserDB;
import com.carematcher.search.LuceneSearch;
import com.carematcher.search.ResultKey;
import com.carematcher.search.SearchType;
import java.io.IOException;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kbuck
 */
public class SearchServlet extends HttpServlet {

    private static enum WildcardMethod {
        PRE,
        POST,
        BOTH,
        NONE
    }
    
    private static final int MAX_RESULTS = 10;
    
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
        
        String url = "/home";
        String searchString = request.getParameter("search-string");
        Map<ResultKey, Object> searchResults;
        LinkedHashSet<User> userResults;
        String searchTime = "0.0 s";
        
        //  If the search contains text, perform lucene search
        if (searchString != null) {
            //  For testing, reindex the database
            if (LuceneSearch.reindex()) {
                //  Perform a search on the query string
                searchResults = userSearch(searchString);
                //  Get the search time & user list
                if (searchResults.containsKey(ResultKey.TOTAL_SEARCH_TIME)) {
                    StringBuilder sb = new StringBuilder();
                    long millis = ((Long)searchResults.get(ResultKey.TOTAL_SEARCH_TIME));
                    double seconds = ((double)millis)/((double)1000);
                    String secs = String.format("%.3f", seconds);
                    sb.append(secs);
                    sb.append(" s");
                    searchTime = sb.toString();
                }
                if (searchResults.containsKey(ResultKey.SEARCH_RESULTS)) {
                    userResults = (LinkedHashSet<User>)searchResults.get(ResultKey.SEARCH_RESULTS);
                }
                else userResults = new LinkedHashSet<User>();
                
                request.setAttribute("searchTime", searchTime);
                request.setAttribute("searchResults", userResults);
                
                url = "/searchResults.jsp";
            }
            else request.setAttribute("errorMessage", "Failed index");
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    /** Perform a Lucene Search based on the supplied query
     * 
     * @param query the query string from the search bar
     * @return a Map keyed by the ResultKey enums for the given search
     */
    private Map<ResultKey, Object> userSearch(String query) {
        Map<ResultKey, Object> allResults = new EnumMap<ResultKey, Object>(ResultKey.class);
        query = query.trim();   //  trim the query in case there is leading/trailing whitespace

        allResults = recursiveResults(0, query, allResults);
        
        return allResults;
    }
    
    private Map<ResultKey, Object> recursiveResults (int recursion, String query, Map<ResultKey, Object> results) {
        switch(recursion) {
            case 0:
                results.putAll(wildcardUserSearch(query, WildcardMethod.NONE));
                break;
            case 1:
                Map<ResultKey, Object> postResults = wildcardUserSearch(query, WildcardMethod.POST);
                updateResults(results, postResults);
                break;
            case 2:
                LuceneSearch.allowLeadingWildcardSearch(true);
                Map<ResultKey, Object> preResults = wildcardUserSearch(query, WildcardMethod.PRE);
                updateResults(results, preResults);
            case 3:
                LuceneSearch.allowLeadingWildcardSearch(true);
                Map<ResultKey, Object> bothResults = wildcardUserSearch(query, WildcardMethod.BOTH);
                updateResults(results, bothResults);
            default:
                break;
        }
        
        if (checkResults(results) < MAX_RESULTS && recursion < 4) {
            recursion++;
            return recursiveResults(recursion, query, results);
        }
        else return results;
    }
    
    /** Perform a query on the literal string from the user, adding specified wildcards
     * 
     * @param literalQuery the user-entered query string
     * @param method the method to apply wildcards, leading, trailing, both or none (literal)
     * @return a search results map, with the SEARCH_RESULTS key added
     */
    private Map<ResultKey, Object> wildcardUserSearch(String literalQuery, WildcardMethod method) {
        StringBuilder wildcardBuilder = new StringBuilder();
        switch(method) {
            case PRE:
                wildcardBuilder.append("*");
                wildcardBuilder.append(literalQuery);
                break;
            case POST:
                wildcardBuilder.append(literalQuery);
                wildcardBuilder.append("*");
                break;
            case BOTH:
                wildcardBuilder.append("*");
                wildcardBuilder.append(literalQuery);
                wildcardBuilder.append("*");
                break;
            default:
                wildcardBuilder.append(literalQuery);
                break;
        }
        Map<ResultKey, Object> searchResults = LuceneSearch.search(wildcardBuilder.toString(), SearchType.USER);
        LinkedHashSet<User> users = getResultingUsers(searchResults);
        searchResults.put(ResultKey.SEARCH_RESULTS, users);
        
        return searchResults;
    }

    /** Based on the results of the Luncne search, get a list of Users from the DB
     * 
     * @param resultmap the Lucene search results
     * @return a Set of User objects for the emails returned by Lucene
     */
    private LinkedHashSet<User> getResultingUsers(Map<ResultKey, Object> resultmap) {
        //  Create a set to store the resulting users
        LinkedHashSet<User> results = new LinkedHashSet<User>();
        
        //  Next check the service results:
        LinkedHashSet<String> mapResults = (LinkedHashSet<String>) resultmap.get(ResultKey.SERVICE);
        if (mapResults != null) {
            for (String servName : mapResults) {
                List<Provider> providers = ProviderDB.selectProvidersThatPerformService(servName);
                System.out.println("providers found: " + providers.size());
                for (Provider p : providers) {
                    User u = (User)p;
                    results.add(u);
                }
            }
        }
        
        
        //  Next check the license results
        mapResults = (LinkedHashSet<String>) resultmap.get(ResultKey.LICENSE);
        if (mapResults != null) {
            for (String liceName : mapResults) {
                License license = LicenseDB.selectLicenseByLicenseName(liceName);
                if (license != null) {
                    Set<Provider> providers = license.getProvidersWithLicense();
                    for (Provider p : providers) {
                        User u = (User)p;
                        results.add(u);
                    }
                }
            }
        }
        
        //  Next check the insurance results
        mapResults = (LinkedHashSet<String>) resultmap.get(ResultKey.INSURANCE);
        if (mapResults != null) {
            for (String policyName : mapResults) {
                Insurance insurance = InsuranceDB.selectInsuranceByPolicyName(policyName);
                if (insurance != null) {
                    Set<Practice> practices = insurance.getAcceptingPractices();
                    for (Practice p : practices) {
                        User u = (User)p;
                        results.add(u);
                    }
                }
            }
        }
        
        //  Lastly check the email results:
        mapResults = (LinkedHashSet<String>) resultmap.get(ResultKey.EMAIL);
        if (mapResults != null) {
            for (String email : mapResults) {
                User result = UserDB.selectUserByEmail(email);
                if (result != null && !result.role().equals(Role.CUSTOMER)) 
                    results.add(result);
            }
        }
        
        return results;
    }
    
    private int checkResults(Map<ResultKey, Object> results) {
        return ((LinkedHashSet<User>) results.get(ResultKey.SEARCH_RESULTS)).size();
    }
    
    /** Update the results of the last search with the current result set
     * 
     * @param results the overall result set
     * @param update the result set from the last search
     * @return false if not MAX_RESULTS met, true otherwise
     */
    private void updateResults(Map<ResultKey, Object> results, Map<ResultKey, Object> update) {
        long total_time = ((Long)results.get(ResultKey.TOTAL_SEARCH_TIME));
        long update_time = ((Long)update.get(ResultKey.TOTAL_SEARCH_TIME));
        total_time += update_time;
        
        LinkedHashSet<User> update_users = (LinkedHashSet<User>)update.get(ResultKey.SEARCH_RESULTS);
        LinkedHashSet<User> total_users = (LinkedHashSet<User>)results.get(ResultKey.SEARCH_RESULTS);
        boolean modified = false;
        for (User u : update_users) {
            if (total_users.size() < MAX_RESULTS) {
                if (total_users.add(u)) {
                    modified = true;
                }
            }
            else break;
        }
        String logcount = "total users: " + total_users.size();
        Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, logcount);
        results.put(ResultKey.TOTAL_SEARCH_TIME, total_time);
        if (modified) {
            results.put(ResultKey.SEARCH_RESULTS, total_users);
        }
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
