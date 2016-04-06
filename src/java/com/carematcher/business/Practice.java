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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author kbuck
 */
@Entity
@Table(name="PRACTICE")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
public class Practice extends User implements Serializable {

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="PRACTICE_PROVIDERS",
                       joinColumns={@JoinColumn(name="PRACTICE_ID", referencedColumnName="USERID")},
                       inverseJoinColumns={@JoinColumn(name="PROVIDER_ID", referencedColumnName="USERID")})
    private final Set<Provider> employedProviders = new HashSet<Provider>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="PRACTICE_INSURANCES",
                       joinColumns={@JoinColumn(name="PRACTICE_ID", referencedColumnName="USERID")},
                       inverseJoinColumns={@JoinColumn(name="INSURANCEID", referencedColumnName="INSURANCEID")})
    private final Set<Insurance> acceptedInsurance = new HashSet<Insurance>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="PRACTICE_SERVICES",
                       joinColumns={@JoinColumn(name="PRACTICE_ID", referencedColumnName="USERID")},
                       inverseJoinColumns={@JoinColumn(name="SERVICEID", referencedColumnName="SERVICEID")})
    private final Set<Service> allServices = new HashSet<Service>();

    public String getPracticeName() {
        return getFirstName();
    }
    
    public void setPracticeName(String practiceName) {
        if (practiceName != null && !practiceName.trim().isEmpty()) {
            this.setFirstName(practiceName.trim());
        }
    }

    public Set<Provider> getEmployedProviders() {
        return employedProviders;
    }

    public void addEmployedProvider(Provider provider) {
        if (provider != null) {
            employedProviders.add(provider);

            if (!provider.isPracticingAt(this)) {
                provider.addEmployingPractice(this);
            }
        }
    }

    public void removeEmployedProvider(Provider provider) {
        if (provider != null && employsProvider(provider)) {
            employedProviders.remove(provider);

            if (provider.isPracticingAt(this)) {
                provider.removeEmployingPractice(this);
            }
        }
    }

    public Set<Insurance> getAcceptedInsurance() {
        return acceptedInsurance;
    }

    public void addAcceptedInsurance(Insurance insurance) {
        if (insurance != null) {
            acceptedInsurance.add(insurance);

            if (!insurance.isAcceptedAtPractice(this)) {
                insurance.addAcceptingPractice(this);
            }
        }
    }

    public void removeAcceptedInsurance(Insurance insurance) {
        if (insurance != null && acceptsInsurance(insurance)) {
            acceptedInsurance.remove(insurance);

            if (insurance.isAcceptedAtPractice(this)) {
                insurance.removeAcceptinPractice(this);
            }
        }
    }

    /**
     *
     * @param insurance
     * @return
     */
    public boolean acceptsInsurance(Insurance insurance) {
        return insurance != null ? acceptedInsurance.contains(insurance) : false;
    }

    /** Checks whether the specified provider works at this practice
     *
     * @param provider the provider to check if is employed by this practice
     * @return true if the provider is contained in the set of employed providers, false otherwise or if provider is null
     */
    public boolean employsProvider(Provider provider) {
        return provider != null ? employedProviders.contains(provider) : false;
    }

    public Set<Service> getAllServices() {
        return allServices;
    }

    public void addService(Service service) {
        if (service != null) {
            allServices.add(service);

            if (!service.isServicedAt(this)) {
                service.addPractice(this);
            }
        }
    }

    public void removeService(Service service) {
        if (service != null && hasService(service)) {
            allServices.remove(service);

            if (service.isServicedAt(this)) {
                service.removePractice(this);
            }
        }
    }

    public boolean hasService(Service service) {
        return service != null ? allServices.contains(service) : false;
    }

    @Override
    public void setLastName(String lastName) {
        // Do nothing
    }

    @Override
    public void setMidInit(String midInit) {
        //  Do Nothing
    }

    @Override
    public String getLastName() {
        return "";
    }

    @Override
    public String getMidInit() {
        return "";
    }

    /** Comparator function to compare Practice objects
     *
     * @param o the Customer object to compare to this object
     * @return true if the hash codes equal
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Practice) {
            Practice a = (Practice) o;

            return a.hashCode() == hashCode();
        }

        return false;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.getEmail() != null ? this.getEmail().hashCode() : 0);
        hash = 97 * hash + (this.getFirstName() != null ? this.getFirstName().hashCode() : 0);
        return hash;
    }
}
