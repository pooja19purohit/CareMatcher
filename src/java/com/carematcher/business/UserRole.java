/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author kbuck
 */
@Entity
@Table(name="USER_ROLE")
public class UserRole implements Serializable {
	
    @Id
    @Column(name="ROLE_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int role_id;
    
    private String roleName;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="role")
    private final Set<User> users = new HashSet<User>();

    public UserRole() { }

    public UserRole(Role role) {
        setRole(role);
    }
    
    public int getRole_id() {
        return role_id;
    }
	
    public Role role() {
        return Role.toRole(role_id);
    }
    
    public String roleName() {
        return roleName;
    }

    public void setRole(Role role) {
        if (role != null) {
            this.role_id = role.ordinal();
            this.roleName = role.toString();
        }
    }

    public Set<User> getUsers() {
        return users;
    }
    
    public boolean hasUser(User user) {
        return user != null ? users.contains(user) : false;
    }

    public void addUserToRole(User user) {
        if (user != null) {
            users.add(user);

            if (!this.equals(user.getUserRole())) {
                user.setUserRole(this);
            }
        }
    }

    public void removeUserFromRole(User user) {
        if (user != null && users.contains(user)) {
            Role role = Role.toRole(role_id);
            if (role.equals(user.getUserRole())) {
                user.clearUserRole();
            }

            users.remove(user);
        }
    }
}
