/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Phone;
import com.carematcher.util.DBUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author kbuck
 */
public class PhoneDB {
    
    public static boolean insert(Phone phone) {
        if (phone == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(phone);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Phone.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean update(Phone phone) {
        if (phone == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(phone);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Phone.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean delete(Phone phone) {
        if (phone == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(phone);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Phone.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
}
