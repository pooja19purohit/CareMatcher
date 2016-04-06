package com.carematcher.business;

import com.carematcher.business.Address;
import com.carematcher.business.Phone;
import com.carematcher.business.UserRole;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> midInit;
    public static volatile SingularAttribute<User, String> imgUrl;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SetAttribute<User, Address> addresses;
    public static volatile SingularAttribute<User, UserRole> role;
    public static volatile SetAttribute<User, Phone> phones;
    public static volatile SingularAttribute<User, String> biography;
    public static volatile SingularAttribute<User, Long> userId;
    public static volatile SingularAttribute<User, String> email;

}