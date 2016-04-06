package com.carematcher.business;

import com.carematcher.business.PhoneType;
import com.carematcher.business.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Phone.class)
public class Phone_ { 

    public static volatile SingularAttribute<Phone, PhoneType> phoneType;
    public static volatile SingularAttribute<Phone, String> phoneNumber;
    public static volatile SingularAttribute<Phone, Long> phoneId;
    public static volatile SingularAttribute<Phone, User> user;

}