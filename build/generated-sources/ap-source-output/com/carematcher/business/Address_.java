package com.carematcher.business;

import com.carematcher.business.Address.AddressType;
import com.carematcher.business.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> zip;
    public static volatile SingularAttribute<Address, String> st;
    public static volatile SingularAttribute<Address, String> city;
    public static volatile SingularAttribute<Address, AddressType> addressType;
    public static volatile SingularAttribute<Address, String> street;
    public static volatile SingularAttribute<Address, Double> latitude;
    public static volatile SingularAttribute<Address, Boolean> primaryAdd;
    public static volatile SingularAttribute<Address, User> user;
    public static volatile SingularAttribute<Address, Long> addressId;
    public static volatile SingularAttribute<Address, Double> longitude;

}