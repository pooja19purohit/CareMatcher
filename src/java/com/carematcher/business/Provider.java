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
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** The main provider class, extension of the user class, storing provider-
 * specific information
 * @author kbuck
 */
@Entity
@Table(name="PROVIDER")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
public class Provider extends User implements Serializable{

    private boolean willTravel = false;
    private boolean acceptingNewPatients = false;
    private String availability = "";
    private int yearsPracticing = 0;

    @ManyToMany(mappedBy="linkedProviders")
    private final Set<Customer> linkedCustomers = new HashSet<Customer>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="PROVIDER_SERVICES",
            joinColumns={@JoinColumn(name="PROVIDER_ID", referencedColumnName="USERID")},
            inverseJoinColumns={@JoinColumn(name="SERVICEID", referencedColumnName="SERVICEID")})
    private final Set<Service> servicesPerformed = new HashSet<Service>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="PROVIDER_LICENSES",
            joinColumns={@JoinColumn(name="PROVIDER_ID", referencedColumnName="USERID")},
            inverseJoinColumns={@JoinColumn(name="LICENSEID", referencedColumnName="LICENSEID")})
    private final Set<License> licenses = new HashSet<License>();

    @ManyToMany(mappedBy="employedProviders")
    private final Set<Practice> employingPractices = new HashSet<Practice>();

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="provider")
    private final Set<Review> reviews = new HashSet<Review>();

    public boolean travels() {
        return willTravel;
    }

    public void setWillTravel(boolean willTravel) {
        this.willTravel = willTravel;
    }

    public boolean isAcceptingNewPatients() {
        return acceptingNewPatients;
    }

    public void setAcceptingNewPatients(boolean acceptingNewPatients) {
        this.acceptingNewPatients = acceptingNewPatients;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        if (availability != null && !availability.trim().isEmpty()) {
            this.availability = availability.trim();
        }
    }

    public int getYearsPracticing() {
        return yearsPracticing;
    }

    public void setYearsPracticing(int yearsPracticing) {
        if (yearsPracticing > 0) {
            this.yearsPracticing = yearsPracticing;
        }
    }

    public Set<Customer> getLinkedCustomers() {
        return linkedCustomers;
    }

    public void addLinkedCustomer(Customer customer) {
        if (customer != null) {
            linkedCustomers.add(customer);

            if (!customer.isLinkedWithProvider(this)) {
                customer.addLinkedProvider(this);
            }
        }
    }

    public void removeLinkedCustomer(Customer customer) {
        if (customer != null && isLinkedWithCustomer(customer)) {
            linkedCustomers.remove(customer);

            if (customer.isLinkedWithProvider(this)) {
                customer.removeLinkedProvider(this);
            }
        }
    }


    public boolean isLinkedWithCustomer(Customer customer) {
        return customer != null ? linkedCustomers.contains(customer) : false;
    }

    public Set<Service> getServicesPerformed() {
        return servicesPerformed;
    }

    public void addServicePerformed(Service service) {
        if (service != null) {
            servicesPerformed.add(service);

            if (!service.isProvidedBy(this)) {
                service.addProvider(this);
            }
        }
    }

    public void removeServicePerformed(Service service) {
        if (service != null && performsService(service)) {
            servicesPerformed.remove(service);

            if (service.isProvidedBy(this)) {
                service.removeProvider(this);
            }
        }
    }

    /** Checks whether this provider performs the specified service
     *
     * @param service the service to check whether this provider performs
     * @return true if the set of services performed contains the specified service, false if service is null or not contained
     */
    public boolean performsService(Service service) {
        return service != null ? servicesPerformed.contains(service) : false;
    }

    public Set<Practice> getEmployingPractices() {
        return employingPractices;
    }

    public void addEmployingPractice(Practice practice) {
        if (practice != null) {
            employingPractices.add(practice);

            if (!practice.employsProvider(this)) {
                practice.addEmployedProvider(this);
            }
        }
    }

    public void removeEmployingPractice(Practice practice) {
        if (practice != null && isPracticingAt(practice)) {
            employingPractices.remove(practice);

            if (practice.employsProvider(this)) {
                practice.removeEmployedProvider(this);
            }
        }
    }

    /** Checks whether this provider works at the specified practice
     *
     * @param practice the practice to check for this provider's employment
     * @return true if the set of practices this provider works at contains the specified practice, false otherwise or if practice is null
     */
    public boolean isPracticingAt(Practice practice) {
        return practice != null ? employingPractices.contains(practice) : false;
    }

    public Set<License> getLicenses() {
        return licenses;
    }

    public void addLicense(License license) {
        if (license != null) {
            licenses.add(license);

            if (!license.hasLicensedProvider(this)) {
                license.addProviderWithLicense(this);
            }
        }
    }

    public void removeLicense(License license) {
        if (license != null && hasLicense(license)) {
            licenses.remove(license);

            if (license.hasLicensedProvider(this)) {
                license.removeProviderWithLicense(this);
            }
        }
    }

    /**
     *
     * @param license
     * @return
     */
    public boolean hasLicense(License license) {
        return license != null ? licenses.contains(license) : false;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        if (review != null) {
            reviews.add(review);
            
            if (!this.equals(review.getProviderReviewed())) {
                review.setProviderReviewed(this);
            }
        }
    }

    public void removeReview(Review review) {
        if (review != null && hasReview(review)) {
            reviews.remove(review);
            
            if (this.equals(review.getProviderReviewed())) {
                review.setProviderReviewed(null);
            }
        }
    }

    public boolean hasReview(Review review) {
        return review != null ? reviews.contains(review) : false;
    }

    public String getOverallRating() {
        int count = reviews.size();
        if (count == 0) return "No ratings yet.";
        double total_score = 0.0;
        for (Review review : reviews) {
            total_score += review.getRating();
        }
        double average = total_score / (double) count;
        return String.format("%.1f", average);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Provider) {
            Provider a = (Provider) o;

            return a.hashCode() == o.hashCode();
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
