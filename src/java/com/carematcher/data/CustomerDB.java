/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Customer;
import com.carematcher.util.DBUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author kbuck
 */
public class CustomerDB {
    
    /* ----------------------------------------------------------------------
     *  Database Modifications
     * ---------------------------------------------------------------------- */
    /**
     * 
     * @param customer 
     * @return  
     */
    public static boolean insert(Customer customer) {
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(customer);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    /**
     * 
     * @param customer 
     * @return  
     */
    public static boolean update(Customer customer) {
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(customer);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    /**
     * 
     * @param customer 
     * @return  
     */
    public static boolean delete(Customer customer) {
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            Customer del = em.find(Customer.class, customer.getUserId());
            em.remove(del);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    /* ----------------------------------------------------------------------
     *  Database Queries
     * ---------------------------------------------------------------------- */
    /**
     * 
     * @param email
     * @return 
     */
    public static Customer selectCustomerByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT c FROM Customer c WHERE c.email = :email";
        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        tq.setParameter("email", email);
        Customer result = null;
        try {
            result = tq.getSingleResult();
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return result;
    }
    
    public static boolean customerExists(String email) {
        return selectCustomerByEmail(email) != null;
    }
    
    /**
     * 
     * @return 
     */
    public static List<Customer> selectAllCustomers() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT c FROM Customer c";
        TypedQuery tq = em.createQuery(query, Customer.class);
        
        List<Customer> results = new ArrayList<Customer>();
        try {
            results.addAll(tq.getResultList());
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return results;
    }
}
