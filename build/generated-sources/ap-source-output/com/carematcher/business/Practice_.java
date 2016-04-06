package com.carematcher.business;

import com.carematcher.business.Insurance;
import com.carematcher.business.Provider;
import com.carematcher.business.Service;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T15:44:30")
@StaticMetamodel(Practice.class)
public class Practice_ extends User_ {

    public static volatile SetAttribute<Practice, Provider> employedProviders;
    public static volatile SetAttribute<Practice, Insurance> acceptedInsurance;
    public static volatile SetAttribute<Practice, Service> allServices;

}