package com.carematcher.business;

import com.carematcher.business.Customer;
import com.carematcher.business.Practice;
import com.carematcher.business.Provider;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Service.class)
public class Service_ { 

    public static volatile SingularAttribute<Service, String> name;
    public static volatile SingularAttribute<Service, String> description;
    public static volatile SingularAttribute<Service, Set> categories;
    public static volatile SetAttribute<Service, Customer> customers;
    public static volatile SingularAttribute<Service, Long> serviceId;
    public static volatile SetAttribute<Service, Provider> providers;
    public static volatile SetAttribute<Service, Practice> practices;

}