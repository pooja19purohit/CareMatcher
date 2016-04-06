/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author kbuck
 */
@Entity
@Table(name="REVIEW")
public class Review implements Serializable {

    @Id
    @Column(name="REVIEWID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long reviewId;

    private double rating = 0.0;
    private String review = "";

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID", referencedColumnName="USERID")
    private Customer customer;

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="PROVIDER_ID", referencedColumnName="USERID")
    private Provider provider;

    public long getReviewId() {
        return reviewId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating >= 0.0 && rating <= 5.0) {
            this.rating = rating;
        }
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        if (review != null && !review.trim().isEmpty()) {
            this.review = review;
        }
    }

    public Customer getReviewingCustomer() {
        return customer;
    }

    public void setReviewingCustomer(Customer customer) {
        if (customer != null) {
            this.customer = customer;

            if (!customer.hasReview(this)) {
                customer.addReviewPosted(this);
            }
        }
    }

    public Provider getProviderReviewed() {
        return provider;
    }

    public void setProviderReviewed(Provider provider) {
        if (provider != null) {
            this.provider = provider;

            if (!provider.hasReview(this)) {
                provider.addReview(this);
            }
        }
    }

    /** Comparator function to compare Review objects
     *
     * @param o the Review object to compare to this object
     * @return true if the hash codes equal
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Review) {
            Review a = (Review) o;

            return a.hashCode() == hashCode();
        }

        return false;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.review != null ? this.review.hashCode() : 0);
        return hash;
    }
}
