package com.carematcher.business;

import com.carematcher.business.Insurance;
import com.carematcher.business.Provider;
import com.carematcher.business.Review;
import com.carematcher.business.Service;
import java.util.GregorianCalendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Customer.class)
public class Customer_ extends User_ {

    public static volatile SetAttribute<Customer, Service> servicesDesired;
    public static volatile SetAttribute<Customer, Insurance> insurances;
    public static volatile SetAttribute<Customer, Review> reviews;
    public static volatile SetAttribute<Customer, Provider> linkedProviders;
    public static volatile SingularAttribute<Customer, GregorianCalendar> dateOfBirth;

}