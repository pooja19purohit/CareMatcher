/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Customer;
import com.carematcher.business.Practice;
import com.carematcher.business.Provider;
import com.carematcher.business.User;
import com.carematcher.business.Role;
import com.carematcher.util.DBUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pooja Purohit
 */
public class UserDB {
    
    /*
     *  No Insert Methods because each user is inserted into it's
     *  own table. Queries independent of user type will appear here
     */
    /** Update a generic user object in the database
     * 
     * @param user
     * @return 
     */
    public static boolean updateUser(User user) {
        if (user == null) return false;
        
        Role role = user.role();
        boolean success = true;
        switch(role) {
            case CUSTOMER:
                success = CustomerDB.update((Customer)user);
                break;
            case PROVIDER:
                success = ProviderDB.update((Provider)user);
                break;
            case PRACTICE:
                success = PracticeDB.update((Practice)user);
                break;
            default:
                break;
        }
        
        return success;
    }
    
    /** Delete a generic user object from the database
     * 
     * @param user
     * @return 
     */
    public static boolean deleteUser(User user) {
        if (user == null) return false;
        
        Role role = user.role();
        boolean success = true;
        switch(role) {
            case CUSTOMER:
                success = CustomerDB.delete(CustomerDB.selectCustomerByEmail(user.getEmail()));
                break;
            case PROVIDER:
                success = ProviderDB.delete((Provider)user);
                break;
            case PRACTICE:
                success = PracticeDB.delete((Practice)user);
                break;
            default:
                break;
        }
        
        return success;
    }
    
    /** Query the Users table for the specified email
     * 
     * @param email the email address to search for
     * @return true if the email is found in the table, false otherwise
     */
    public static boolean emailExists(String email) {
        return selectUserByEmail(email) != null;
    }
    
    /** Performs a SELECT from the Users table given the specified email to return
     *  the user associated to the email address
     * 
     * @param email the email address to search
     * @return the User object from the User table for the specified user
     */
    public static User selectUserByEmail(String email) {
        if (email != null) {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            String query = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> tq = em.createQuery(query, User.class);
            tq.setParameter("email", email);
            
            User result = null;
            try {
                result = tq.getSingleResult();
            } catch(Exception e) {
                
            } finally {
                em.close();
            }
            
            return result;
        }
        else return null;
    }
    
    

    
    /** Selects all users from the User table
     * 
     * @return 
     */
    public static List<User> selectAllUsers() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String[] queries = {
            "SELECT c FROM Customer c",
            "SELECT p FROM Provider p",
            "SELECT p FROM Practice p"
        };
        
        List<User> users = new ArrayList<User>();
        for (String query : queries) {
            TypedQuery<User> tq = em.createQuery(query, User.class);
            try {
                users.addAll(tq.getResultList());
            } catch(Exception e) {
                
            }
        }
        em.close();
        
        return users;
    }
} 
