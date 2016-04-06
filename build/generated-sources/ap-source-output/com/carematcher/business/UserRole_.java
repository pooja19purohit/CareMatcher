package com.carematcher.business;

import com.carematcher.business.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(UserRole.class)
public class UserRole_ { 

    public static volatile SingularAttribute<UserRole, Integer> role_id;
    public static volatile SingularAttribute<UserRole, String> roleName;
    public static volatile SetAttribute<UserRole, User> users;

}