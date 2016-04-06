/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.RememberedUser;
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
public class RememberedUserDB {
    
    public static boolean insert(RememberedUser rUser) {
        if (rUser == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(rUser);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(RememberedUser.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        return success;
    }
    
    public static boolean update(RememberedUser rUser) {
        if (rUser == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(rUser);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(RememberedUser.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        return success;
    }
    
    public static boolean delete(RememberedUser rUser) {
        if (rUser == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(rUser);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(RememberedUser.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        return success;
    }
    
    public static RememberedUser selectRememberedUserByUsername(String username) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT r FROM RememberedUsers r "
                     + "WHERE r.USERNAME = :username";
        TypedQuery<RememberedUser> tq = em.createQuery(query, RememberedUser.class);
        RememberedUser result = null;
        try {
            result = tq.getSingleResult();
        } catch (NoResultException e) {
            //  No Remember users found
        } catch (NonUniqueResultException e) {
            //  Multiple remembered users with username found
        } catch (Exception e) {
            //  General exception
        } finally {
            em.close();
        }
        
        return result;
    }
}
