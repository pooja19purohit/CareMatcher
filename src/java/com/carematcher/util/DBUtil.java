package com.carematcher.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** This is the DBUtil Class from the book, I've used it here for convenience
 * 
 * 
 */
public class DBUtil {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("CareMatcherPU");
    
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
