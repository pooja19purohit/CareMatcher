/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Insurance;
import com.carematcher.util.DBUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author kbuck
 */
public class InsuranceDB {
    
    public static boolean insert(Insurance insurance) {
        if (insurance == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(insurance);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Insurance.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean update(Insurance insurance) {
        if (insurance == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(insurance);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Insurance.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean delete(Insurance insurance) {
        if (insurance == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(insurance);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Insurance.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static Insurance selectInsuranceByPolicyName(String policyName) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT i FROM Insurance i WHERE i.policyName = :policyName";
        TypedQuery<Insurance> tq = em.createQuery(query, Insurance.class);
        tq.setParameter("policyName", policyName);
        
        Insurance result = null;
        try {
            result = tq.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(InsuranceDB.class.getName()).log(Level.SEVERE, "Exception ", e);
        } finally {
            em.close();
        }
        
        return result;
    }
}
