package com.carematcher.business;

import com.carematcher.business.Customer;
import com.carematcher.business.Provider;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Review.class)
public class Review_ { 

    public static volatile SingularAttribute<Review, Provider> provider;
    public static volatile SingularAttribute<Review, String> review;
    public static volatile SingularAttribute<Review, Double> rating;
    public static volatile SingularAttribute<Review, Long> reviewId;
    public static volatile SingularAttribute<Review, Customer> customer;

}