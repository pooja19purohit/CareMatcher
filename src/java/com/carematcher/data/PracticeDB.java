/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Practice;
import com.carematcher.util.DBUtil;
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
public class PracticeDB {
    
    public static boolean insert(Practice practice) {
        if (practice == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(practice);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Practice.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean update(Practice practice) {
        if (practice == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(practice);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Practice.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean delete(Practice practice) {
        if (practice == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(practice);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Practice.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static Practice selectPracticeByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT p FROM PRACTICE p WHERE p.email = :email";
        TypedQuery<Practice> tq = em.createQuery(query, Practice.class);
        tq.setParameter("email", email);
        Practice result = null;
        try {
            result = tq.getSingleResult();
        } catch(NoResultException e) {
            
        } catch(NonUniqueResultException e) {
            
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return result;
    }
} 
