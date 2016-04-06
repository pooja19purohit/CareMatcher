/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.data;

import com.carematcher.business.Customer;
import com.carematcher.business.Role;
import com.carematcher.business.UserRole;

/**
 *
 * @author kbuck
 */
public class _DbTablesCreate {
    
    public static void generateTables() {
        Customer c = new Customer();
        UserRole role = new UserRole();
        
        CustomerDB.insert(c);
        UserRoleDB.insertRole(role);
        
        UserRoleDB.removeUserFromRole(c, Role.CUSTOMER);
        CustomerDB.delete(c);
    }
}
