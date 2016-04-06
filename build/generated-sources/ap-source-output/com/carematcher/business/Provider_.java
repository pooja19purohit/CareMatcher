package com.carematcher.business;

import com.carematcher.business.Customer;
import com.carematcher.business.License;
import com.carematcher.business.Practice;
import com.carematcher.business.Review;
import com.carematcher.business.Service;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Provider.class)
public class Provider_ extends User_ {

    public static volatile SetAttribute<Provider, License> licenses;
    public static volatile SetAttribute<Provider, Review> reviews;
    public static volatile SetAttribute<Provider, Service> servicesPerformed;
    public static volatile SetAttribute<Provider, Practice> employingPractices;
    public static volatile SingularAttribute<Provider, String> availability;
    public static volatile SingularAttribute<Provider, Boolean> willTravel;
    public static volatile SetAttribute<Provider, Customer> linkedCustomers;
    public static volatile SingularAttribute<Provider, Integer> yearsPracticing;
    public static volatile SingularAttribute<Provider, Boolean> acceptingNewPatients;

}