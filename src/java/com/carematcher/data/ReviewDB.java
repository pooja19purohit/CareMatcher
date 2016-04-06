/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Review;
import com.carematcher.util.DBUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author kbuck
 */
public class ReviewDB {
    
    public static boolean insert(Review review) {
        if (review == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(review);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    } 
    
    public static boolean update(Review review) {
        if (review == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(review);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return false;
    }
    
    public static boolean delete(Review review) {
        if (review == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(review);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Review.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
}
