package com.carematcher.business;

import com.carematcher.business.Provider;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(License.class)
public class License_ { 

    public static volatile SetAttribute<License, Provider> providersWithLicense;
    public static volatile SingularAttribute<License, String> licenseDescription;
    public static volatile SingularAttribute<License, String> licenseName;
    public static volatile SingularAttribute<License, Long> licenseId;

}