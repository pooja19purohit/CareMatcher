/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Provider;
import com.carematcher.business.Service;
import com.carematcher.util.DBUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author kbuck
 */
public class ProviderDB {
    
    /* ----------------------------------------------------------------------
     *  Database Modifications
     * ---------------------------------------------------------------------- */
    /** Persist a Provider
     * 
     * @param provider the Provider object to insert into the DB
     * @return 
     */
    public static boolean insert(Provider provider) {
        if (provider == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(provider);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    /** Merge an existing Provider
     * 
     * @param provider the Provider to update in the DB
     * @return 
     */
    public static boolean update(Provider provider) {
        if (provider == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(provider);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    /** Remove a provider
     * 
     * @param provider the Provider to delete from the DB
     * @return 
     */
    public static boolean delete(Provider provider) {
        if (provider == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(provider);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    /* ----------------------------------------------------------------------
     *  Database Queries
     * ---------------------------------------------------------------------- */
    /** Get a list of all providers
     * 
     * @return a List of providers in the Database
     */
    public static List<Provider> selectAllProviders() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT p FROM Provider p ";
        TypedQuery<Provider> tq = em.createQuery(query, Provider.class);
        List<Provider> results = new ArrayList<Provider>();
        try {
            results.addAll(tq.getResultList());
        } catch (NoResultException e) {
            
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return results;
    }
    
    /** Select a specific provider based on the provider email
     * 
     * @param email the email for the registered provider
     * @return the Provider object result from the DB query
     */
    public static Provider selectProviderByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT p FROM Provider p WHERE p.email = :email";
        TypedQuery<Provider> tq = em.createQuery(query, Provider.class);
        tq.setParameter("email", email);
        Provider provider = null;
        try {
            provider = tq.getSingleResult();
        } catch (NoResultException e) {
            
        } catch (NonUniqueResultException e) {
       
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return provider;
    }
    
    public static List<Provider> selectProvidersThatPerformService(String serviceName) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT p FROM Provider p JOIN p.servicesPerformed s WHERE s.name LIKE :serviceName";
        TypedQuery<Provider> tq = em.createQuery(query, Provider.class);
        //Set<Service> services = ServiceDB.selectAllServices();
        tq.setParameter("serviceName", "%" + serviceName +"%");
        
        List<Provider> results = new ArrayList<Provider>();
        try {
            results.addAll(tq.getResultList());
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return results;
    }
}
