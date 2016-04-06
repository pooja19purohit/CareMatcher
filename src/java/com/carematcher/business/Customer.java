/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import com.carematcher.util.CalUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** The main Customer class, extension of the User class, storing customer-
 * specific attributes
 * @author kbuck
 */
@Entity
@Table(name="CUSTOMER")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
public class Customer extends User implements Serializable{

    @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar dateOfBirth = new GregorianCalendar();

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="customer")
    private final Set<Review> reviews = new HashSet<Review>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="CUSTOMER_SERVICES",
                       joinColumns={@JoinColumn(name="CUST_ID", referencedColumnName="USERID")},
                       inverseJoinColumns={@JoinColumn(name="SERVICEID", referencedColumnName="SERVICEID")})
    private final Set<Service> servicesDesired = new HashSet<Service>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="CUSTOMER_INSURANCES",
                       joinColumns={@JoinColumn(name="CUST_ID", referencedColumnName="USERID")},
                       inverseJoinColumns={@JoinColumn(name="INSURANCEID", referencedColumnName="INSURANCEID")})
    private final Set<Insurance> insurances = new HashSet<Insurance>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="CUSTOMER_PROVIDERS",
                       joinColumns={@JoinColumn(name="CUST_ID", referencedColumnName="USERID")},
                       inverseJoinColumns={@JoinColumn(name="PROVIDER_ID", referencedColumnName="USERID")})
    private final Set<Provider> linkedProviders = new HashSet<Provider>();

    public GregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dob) {
        if (dob != null) {
            this.dateOfBirth.setTime(dob);
            CalUtil calendar = new CalUtil(dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH),
                                           dateOfBirth.get(GregorianCalendar.MONTH),
                                           dateOfBirth.get(GregorianCalendar.YEAR));
            if (!calendar.isValidDate()) {
                this.dateOfBirth = new GregorianCalendar();
            }
        }
    }

    public Set<Provider> getLinkedProviders() {
        return linkedProviders;
    }

    public void addLinkedProvider(Provider provider) {
        if (provider != null) {
            linkedProviders.add(provider);

            if (!provider.isLinkedWithCustomer(this)) {
                provider.addLinkedCustomer(this);
            }
        }
    }

    public void removeLinkedProvider(Provider provider) {
        if (provider != null && isLinkedWithProvider(provider)) {
            linkedProviders.remove(provider);

            if (provider.isLinkedWithCustomer(this)) {
                provider.removeLinkedCustomer(this);
            }
        }
    }

    /** Gets whether this customer is linked with the specified provider
     *
     * @param provider the specified provider to check existence in the set of linked providers
     * @return true if the set contains the specified provider, false if not contained or null provider
     */
    public boolean isLinkedWithProvider(Provider provider) {
        return provider != null ? linkedProviders.contains(provider) : false;
    }

    public Set<Review> getReviewsPosted() {
        return reviews;
    }
    
    public boolean hasReviewedProvider(User u) {
        if (u instanceof Provider) {
            Provider p = (Provider)u;
            for (Review r : reviews) {
                if (r.getProviderReviewed().equals(p))
                    return true;
            }
            return false;
        }
        else return false;
    }

    public void addReviewPosted(Review review) {
        if (review != null) {
            reviews.add(review);

            if (!this.equals(review.getReviewingCustomer())) {
                review.setReviewingCustomer(this);
            }
        }
    }

    public void removePostedReview(Review review) {
        if (review != null && hasReview(review)) {
            reviews.remove(review);

            if (this.equals(review.getReviewingCustomer())) {
                review.setReviewingCustomer(null);
            }
        }
    }

    /**
     *
     * @param review
     * @return
     */
    public boolean hasReview(Review review) {
        return review != null ? reviews.contains(review) : false;
    }

    public Set<Service> getServicesDesired() {
        return servicesDesired;
    }

    public void addDesiredService(Service service) {
        if (service != null) {
            servicesDesired.add(service);

            if (!service.isDesiredBy(this)) {
                service.addCustomer(this);
            }
        }
    }

    public void removeDesiredService(Service service) {
        if (service != null && isServiceDesired(service)) {
            servicesDesired.remove(service);

            if (service.isDesiredBy(this)) {
                service.removeCustomer(this);
            }
        }
    }

    /**
     *
     * @param service
     * @return
     */
    public boolean isServiceDesired(Service service) {
        return service != null ? servicesDesired.contains(service) : false;
    }

    public Set<Insurance> getInsurance() {
        return insurances;
    }

    public void addInsurance(Insurance insurance) {
        if (insurance != null) {
            insurances.add(insurance);

            if (!insurance.hasCustomer(this)) {
                insurance.addCustomer(this);
            }
        }
    }

    public void removeInsurance(Insurance insurance) {
        if (insurance != null && hasInsurance(insurance)) {
            insurances.remove(insurance);

            if (insurance.hasCustomer(this)) {
                insurance.removeCustomer(this);
            }
        }
    }

    /**
     *
     * @param insurance
     * @return
     */
    public boolean hasInsurance(Insurance insurance) {
        return insurance != null ? insurances.contains(insurance) : false;
    }

    /** Comparator function to compare Customer objects
     *
     * @param o the Customer object to compare to this object
     * @return true if the hash codes equal
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Customer) {
            Customer a = (Customer) o;

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
