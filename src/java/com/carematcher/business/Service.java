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
@Table(name="SERVICE")
public class Service implements Serializable {

    @Id
    @Column(name="SERVICEID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long serviceId;

    private String name;

    private String description;

    private final Set<String> categories = new HashSet<String>();

    @ManyToMany(mappedBy="servicesPerformed")
    private final Set<Provider> providers = new HashSet<Provider>();

    @ManyToMany(mappedBy="servicesDesired")
    private final Set<Customer> customers = new HashSet<Customer>();

    @ManyToMany(mappedBy="allServices")
    private final Set<Practice> practices = new HashSet<Practice>();


    public long getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description.trim();
        }
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void addCategory(String category) {
        if (category != null && !category.trim().isEmpty()) {
            categories.add(category.trim());
        }
    }

    public void removeCategory(String category) {
        if (category != null && isInCategory(category)) {
            categories.remove(category);
        }
    }

    public boolean isInCategory(String category) {
        return category != null ? categories.contains(category) : false;
    }

    public Set<Provider> getProviders() {
        return providers;
    }

    public void addProvider(Provider provider) {
        if (provider != null) {
            providers.add(provider);

            if (!provider.performsService(this)) {
                provider.addServicePerformed(this);
            }
        }
    }

    public void removeProvider(Provider provider) {
        if (provider != null && isProvidedBy(provider)) {
            providers.remove(provider);

            if (provider.performsService(this)) {
                provider.removeServicePerformed(this);
            }
        }
    }

    /** Checks whether the specified provider performs this service
     *
     * @param provider the specified provider to check if provides this service
     * @return true if the specified provider is in the set of providers, false if not in the set or is null
     */
    public boolean isProvidedBy(Provider provider){
        return provider != null ? providers.contains(provider) : false;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);

            if (!customer.isServiceDesired(this)) {
                customer.addDesiredService(this);
            }
        }
    }

    public void removeCustomer(Customer customer) {
        if (customer != null && isDesiredBy(customer)) {
            customers.remove(customer);

            if (customer.isServiceDesired(this)) {
                customer.removeDesiredService(this);
            }
        }
    }

    /**
     *
     * @param customer
     * @return
     */
    public boolean isDesiredBy(Customer customer) {
        return customer != null ? customers.contains(customer) : false;
    }

    public Set<Practice> getPractices() {
        return practices;
    }

    public void addPractice(Practice practice) {
        if (practice != null) {
            practices.add(practice);

            if (!practice.hasService(this)) {
                practice.addService(this);
            }
        }
    }

    public void removePractice(Practice practice) {
        if (practice != null && isServicedAt(practice)) {
            practices.remove(practice);

            if (practice.hasService(this)) {
                practice.removeService(this);
            }
        }
    }

    /**
     *
     * @param practice
     * @return
     */
    public boolean isServicedAt(Practice practice) {
        return practice != null ? practices.contains(practice) : false;
    }

    /** Comparator function to compare Service objects
     *
     * @param o the Service object to compare to this object
     * @return true if the hash codes equal
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Service) {
            Service a = (Service) o;

            return a.hashCode() == hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }
 }