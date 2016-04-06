/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Service;
import com.carematcher.util.DBUtil;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pooja Purohit
 */
public class ServiceDB {
    
    public static boolean insert(Service service) {
        if (service == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(service);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean update(Service service) {
        if (service == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(service);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean delete(Service service) {
        if (service == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(service);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static Service selectServiceByName(String name) {
        if (name == null) return null;
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT s FROM Service s WHERE s.name = :name";
        TypedQuery<Service> tq = em.createQuery(query, Service.class);
        tq.setParameter("name", name);
        
        Service result = null;
        try {
            result = tq.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(ServiceDB.class.getName()).log(Level.SEVERE, "Exception: ", e);
        } finally {
            em.close();
        }
        
        return result;
    }
    
    public static Set<Service> selectAllServices() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT s FROM Service s";
        TypedQuery<Service> tq = em.createQuery(query, Service.class);
        
        Set<Service> results = new HashSet<Service>();
        try {
            results.addAll(tq.getResultList());
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return results;
    }
    
    public static boolean serviceExists(String name) {
        return selectServiceByName(name) != null;
    }
}
