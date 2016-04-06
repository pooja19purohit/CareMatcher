/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address implements Serializable {

    public enum AddressType {
        HOME,
        WORK,
        MAILING
    }
    private AddressType addressType = AddressType.HOME;

    @Id
    @Column(name="ADDRESSID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long addressId;

    private String street;
    private String city;
    private String st;
    private String zip;

    private double latitude;
    private double longitude;

    private boolean primaryAdd = false;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USERID")
    private User user;

    public long getAddressId() {
        return addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street != null && !street.trim().isEmpty()) {
            this.street = street.trim();
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city != null && !city.trim().isEmpty()) {
            this.city = city.trim();
        }
    }

    public String getSt() {
        return st;
    }

    public void setSt(String state) {
        if (state != null && !state.trim().isEmpty()) {
            this.st = state.trim();
        }
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        if (zip != null && !zip.trim().isEmpty()) {
            this.zip = zip.trim();
        }
    }

    public double getLatitude() {
        return latitude;
    }
    
    public String getLatString() {
        return String.valueOf(latitude);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
    public String getLonString() {
        return String.valueOf(longitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isPrimary() {
        return primaryAdd;
    }

    public void setPrimary(boolean primary) {
        this.primaryAdd = primary;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType type) {
        this.addressType = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        if (user != null && !user.hasAddress(this)) {
            user.addAddress(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (street != null && city != null && st != null && zip != null) {
            sb.append(street);
            sb.append(", ");
            sb.append(city);
            sb.append(", ");
            sb.append(st);
            sb.append(", ");
            sb.append(zip);
        }

        return sb.toString();
    }

    /** Comparator function to compare Address objects
     *
     * @param o the Address object to compare to this object
     * @return true if the hash codes equal
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Address) {
            Address a = (Address) o;

            return a.hashCode() == o.hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.street != null ? this.street.hashCode() : 0);
        hash = 97 * hash + (this.city != null ? this.city.hashCode() : 0);
        return hash;
    }
}
