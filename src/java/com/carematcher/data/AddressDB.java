/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Address;
import com.carematcher.business.User;
import com.carematcher.util.DBUtil;
import com.carematcher.util.QueryBuilder;
import com.carematcher.util.QueryBuilder.QueryType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author kbuck
 */
public class AddressDB {
    
    public static boolean insert(Address address) {
        if (address == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.persist(address);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean update(Address address) {
        if (address == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.merge(address);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
    public static boolean delete(Address address) {
        if (address == null) return false;
        
        boolean success = true;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            if (!trans.isActive()) trans.begin();
            em.remove(address);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, e);
            success = false;
        } finally {
            em.close();
        }
        
        return success;
    }
    
//    public static Address selectUserAddressByAddress(User user, Address address) {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        QueryBuilder qb = new QueryBuilder(QueryType.SELECT);
//        qb.setTable("Address");
//        String street = address.getStreet();
//        String city = address.getCity();
//        String st = address.getSt();
//        String zip = address.getZip();
//        if (street != null) qb.addClause("street");
//        if (city != null) qb.addClause("city");
//        if (st != null) qb.addClause("st");
//        if (zip != null) qb.addClause("zip");
//        qb.joinTable("user", "userId");
//        String query = qb.getQuery();
//        Logger.getLogger(AddressDB.class.getName()).log(Level.INFO, query);
//        TypedQuery<Address> tq = em.createQuery(query, Address.class);
//        if (street != null) tq.setParameter("street", street);
//        if (city != null) tq.setParameter("city", city);
//        if (st != null) tq.setParameter("st", st);
//        if (zip != null) tq.setParameter("zip", zip);
//        tq.setParameter("userId", user.getUserId());
//        Logger.getLogger(AddressDB.class.getName()).log(Level.INFO, query);
//        Logger.getLogger(AddressDB.class.getName()).log(Level.INFO, address.getStreet());
//        Logger.getLogger(AddressDB.class.getName()).log(Level.INFO, address.getCity());
//        Logger.getLogger(AddressDB.class.getName()).log(Level.INFO, address.getSt());
//        Logger.getLogger(AddressDB.class.getName()).log(Level.INFO, address.getZip());
//        Address result = null;
//        try {
//            result = tq.getSingleResult();
//        } catch (NoResultException e) {
//            Logger.getLogger(AddressDB.class.getName()).log(Level.WARNING, "No address found");
//        } catch (Exception e) {
//            Logger.getLogger(AddressDB.class.getName()).log(Level.WARNING, "exception: ", e);
//        } finally {
//            em.close();
//        }
//        
//        return result;
//    }
    
    public static Address selectAddressByValues(String street, String city, String st, String zip) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //  Get the parameters & map them to their query names
        Map<String, String> map = new HashMap<String, String>();
        map.put("street", street);
        map.put("city", city);
        map.put("st", st);
        map.put("zip", zip);
        //  Create the query builder
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a FROM Address a ");
        boolean where = false;
        for (String val : map.keySet()) {
            if (map.get(val) == null) continue;
            if (!where) {
                sb.append("WHERE a.");
                where = true;
            }
            else sb.append("AND a.");
            
            sb.append(val);
            sb.append("=:");
            sb.append(val);
        }
        String query = sb.toString();
        Logger.getLogger(AddressDB.class.getName()).log(Level.INFO, query);
        //  Create the typed query from the query builder
        TypedQuery<Address> tq = em.createQuery(query, Address.class);
        for (String val : map.keySet()) {
            if (map.get(val) != null) tq.setParameter(val, map.get(val));
        }
        
        Address result = null;
        try {
            tq.getSingleResult();
        } catch(Exception e) {
            Logger.getLogger(AddressDB.class.getName()).log(Level.SEVERE, "exception: ", e);
        } finally {
            em.close();
        }
        
        return result;
    }
    
    public static List<Address> selectAddresses() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT a FROM Address a";
        TypedQuery<Address> tq = em.createQuery(query, Address.class);
        List<Address> addresses = new ArrayList<Address>();
        try {
            addresses.addAll(tq.getResultList());
        } catch (NoResultException e) {
            // No Addresses in the DB
        } catch (Exception e) { 
        
        }
        finally {
            em.close();
        }
        
        return addresses;
    }
}
