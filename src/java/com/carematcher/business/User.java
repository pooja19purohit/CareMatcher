/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import com.carematcher.util.PasswordHash;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/** Generic User class, to be extended by Customer, Provider & Practice
 *
 * @author kbuck
 */
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER")
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @Column(name="USERID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String firstName;
    private String midInit;
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String biography = "";

    //	Profile image url -> defaults to placeholder image
    private String imgUrl = "images/200x200.jpg";

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="ROLE_ID")
    protected UserRole role;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="user")
    private final Set<Address> addresses = new HashSet<Address>();

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="user")
    private final Set<Phone> phones = new HashSet<Phone>();

    public long getUserId() {
            return userId;
    }

    public String getFirstName() {
            return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName.trim();
        }
    }

    public String getLastName() {
        return (firstName.equals(lastName)) ? "" : lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = lastName.trim();
        }
    }

    public String getMidInit() {
        return ("_".equals(midInit)) ? "" : midInit;
    }

    public void setMidInit(String midInit) {
        if (midInit != null  && !midInit.trim().isEmpty()) {
            this.midInit = midInit.trim();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.email = email.trim();
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && !password.trim().isEmpty()) {
            try {
                this.password = PasswordHash.createHash(password);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        if (biography != null && !biography.trim().isEmpty()) {
            this.biography = biography.trim();
        }
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        if (imgUrl != null && !imgUrl.trim().isEmpty()) {
            this.imgUrl = imgUrl.trim();
        }
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        if (address != null) {
            addresses.add(address);

            if (!this.equals(address.getUser())) {
                address.setUser(this);
            }

            if (address.isPrimary()) {
                for (Address a : addresses) {
                    if (!a.equals(address)) {
                        a.setPrimary(false);
                    }
                }
            }
        }
    }

    public void removeAddress(Address address) {
        if (address != null && addresses.contains(address)) {
            addresses.remove(address);

            if (this.equals(address.getUser())) {
                address.setUser(null);
            }
        }
    }

    public boolean hasAddress(Address address) {
        return address != null ? addresses.contains(address) : false;
    }

    public Address getPrimaryAddress() {
        for (Address a : addresses) {
            if (a.isPrimary()) return a;
        }
        return null;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void addPhone(Phone phone) {
        if (phone != null) {
            phones.add(phone);

            if (!this.equals(phone.getUser())) {
                phone.setUser(this);
            }
        }
    }

    public void removePhone(Phone phone) {
       if (phone != null && hasPhone(phone)) {
           phones.remove(phone);

           if (this.equals(phone.getUser())) {
               phone.setUser(null);
           }
       }
    }

    public boolean hasPhone(Phone phone) {
        return phone != null ? phones.contains(phone) : false;
    }

    public UserRole getUserRole() {
        return role;
    }
    
    public Role role() {
        return role != null ? role.role() : Role.DEFAULT;
    }

    public void clearUserRole() {
        role = null;
    }
    
    public void setUserRole(UserRole userrole) {
        if (userrole != null) {
           role = userrole;
        
            if (!role.hasUser(this)) {
                role.addUserToRole(this);
            } 
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof User) {
            User a = (User) o;

            return a.hashCode() == hashCode();
        }

        return false;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (email != null ? email.hashCode() : 0);
        hash = 97 * hash + (firstName != null ? firstName.hashCode() : 0);
        return hash;
    }
}