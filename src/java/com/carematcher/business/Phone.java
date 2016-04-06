/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PHONE")
public class Phone implements Serializable {

    private PhoneType phoneType = PhoneType.HOME;

    @Id
    @Column(name="PHONEID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long phoneId;

    private String phoneNumber = "";

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USERID")
    private User user;

    public long getPhoneId() {
        return phoneId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            this.phoneNumber = phoneNumber.trim();
        }
    }

    public String getPhoneTypeString() {
        return phoneType.toString();
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType type) {
        this.phoneType = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        if (user != null && !user.hasPhone(this)) {
            user.addPhone(this);
        }
    }

    /** Comparator function to compare Phone objects
     *
     * @param o the Phone object to compare to this object
     * @return true if the phone equals this phone
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Phone) {
            Phone a = (Phone) o;

            return a.hashCode() == o.hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0);
        hash = 97 * hash + (this.phoneType != null ? this.phoneType.toString().hashCode() : 0);
        return hash;
    }
}
