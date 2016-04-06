/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Role;
import com.carematcher.business.User;
import com.carematcher.business.UserRole;
import com.carematcher.util.DBUtil;
import java.util.HashSet;
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
 * @author Pooja Purohit
 */
public class UserRoleDB {
    
    public static boolean insertRole(UserRole role) {
        if (role == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(role);
            trans.commit();
        } catch (Exception e) {
            Logger.getLogger(UserRole.class.getName()).log(Level.SEVERE, null, e);
            if (trans.isActive()) trans.rollback();
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean updateRole(UserRole role) {
        if (role == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(role);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(UserRole.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean roleExists(Role role) {
        return selectUserRoleByRole(role) != null;
    }
    
    public static UserRole selectUserRoleByRole(Role role) {
        if (role == null) return null;
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT r FROM UserRole r WHERE r.roleName = :roleName";
        TypedQuery<UserRole> tq = em.createQuery(query, UserRole.class);
        tq.setParameter("roleName", role.toString());
        UserRole result = null;
        try {
            result = tq.getSingleResult();
        } catch (NoResultException e) {
            //  Role does not exist yet, so create it, adding it to the DB
            result = new UserRole(role);
            if (!insertRole(result)) {
                result = null;
            }
        } catch (NonUniqueResultException e) {
            
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        
        return result;
    }
    
    public static UserRole selectUserRoleByRoleId(int role_id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT u FROM UserRole u WHERE u.role_id = :role_id";
        TypedQuery<UserRole> tq = em.createQuery(query, UserRole.class);
        tq.setParameter("role_id", role_id);
        UserRole result = null;
        try {
            result = tq.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserRoleDB.class.getName()).log(Level.SEVERE, "Exception: ", e);
        } finally {
            em.close();
        }
        
        return result;
    }
    
    public static Set<User> selectUsersForRole(Role role) {
        UserRole ur = selectUserRoleByRole(role);
        if (ur != null) {
            return ur.getUsers();
        }
        else return new HashSet<User>();
    }
    
//    public static Set<Role> selectRolesForUser(User user) {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        
//    }
    
    public static boolean insertUserToRole(User user, Role role) {
        UserRole ur = selectUserRoleByRole(role);
        if (ur == null) return false;
        else if (ur.getUsers().contains(user)) return false;
        else {
            ur.addUserToRole(user);
            return updateRole(ur);
        }
    }
    
    public static boolean removeUserFromRole(User user, Role role) {
        UserRole ur = selectUserRoleByRole(role);
        if (ur == null) return false;
        else if (!ur.getUsers().contains(user)) return false;
        else {
            ur.removeUserFromRole(user);
            return updateRole(ur);
        }
    }
}
