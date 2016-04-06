package com.carematcher.business;

import com.carematcher.business.Customer;
import com.carematcher.business.Practice;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Insurance.class)
public class Insurance_ { 

    public static volatile SetAttribute<Insurance, Practice> acceptingPractices;
    public static volatile SingularAttribute<Insurance, String> policyName;
    public static volatile SingularAttribute<Insurance, String> companyName;
    public static volatile SingularAttribute<Insurance, Long> insuranceId;
    public static volatile SetAttribute<Insurance, Customer> customers;

}