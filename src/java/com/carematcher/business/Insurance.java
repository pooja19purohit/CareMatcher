/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author kbuck
 */
@Entity
@Table(name="INSURANCE")
public class Insurance implements Serializable {

    @Id
    @Column(name="INSURANCEID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long insuranceId;

    private String companyName;

    private String policyName;

    @ManyToMany(mappedBy="acceptedInsurance")
    private final Set<Practice> acceptingPractices = new HashSet<Practice>();

    @ManyToMany(mappedBy="insurances")
    private final Set<Customer> customers = new HashSet<Customer>();

    public long getInsuranceId() {
        return insuranceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if (companyName != null && !companyName.trim().isEmpty()) {
            this.companyName = companyName;
        }
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        if (policyName != null && !policyName.trim().isEmpty()) {
            this.policyName = policyName;
        }
    }

    public Set<Practice> getAcceptingPractices() {
        return acceptingPractices;
    }

    public void addAcceptingPractice(Practice practice) {
        if (practice != null) {
            acceptingPractices.add(practice);

            if (!practice.acceptsInsurance(this)) {
                practice.addAcceptedInsurance(this);
            }
        }
    }

    public void removeAcceptinPractice(Practice practice) {
        if (practice != null && isAcceptedAtPractice(practice)) {
            acceptingPractices.remove(practice);

            if (practice.acceptsInsurance(this)) {
                practice.removeAcceptedInsurance(this);
            }
        }
    }

    /**
     *
     * @param practice
     * @return
     */
    public boolean isAcceptedAtPractice(Practice practice) {
        return practice != null ? acceptingPractices.contains(practice) : false;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);

            if (!customer.hasInsurance(this)) {
                customer.addInsurance(this);
            }
        }
    }

    public void removeCustomer(Customer customer) {
        if (customer != null && hasCustomer(customer)) {
            customers.remove(customer);

            if (customer.hasInsurance(this)) {
                customer.removeInsurance(this);
            }
        }
    }

    /**
     *
     * @param customer
     * @return
     */
    public boolean hasCustomer(Customer customer) {
        return customer != null ? customers.contains(customer) : false;
    }

    /** Comparator function to compare Insurance objects
     *
     * @param o the Insurance object to compare to this object
     * @return true if the hash codes match
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Insurance) {
            Insurance a = (Insurance) o;

            return a.hashCode() == o.hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.policyName != null ? this.policyName.hashCode() : 0);
        hash = 97 * hash + (this.companyName != null ? this.companyName.hashCode() : 0);
        return hash;
    }
}
