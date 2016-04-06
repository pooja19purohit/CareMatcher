/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author kbuck
 */
@Entity
@Table(name="LICENSE")
public class License implements Serializable {

    @Id
    @Column(name="LICENSEID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long licenseId;

    private String licenseName;
    private String licenseDescription;

    @ManyToMany(mappedBy="licenses")
    private final Set<Provider> providersWithLicense = new HashSet<Provider>();

    public long getLicenseId() {
        return licenseId;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        if (licenseName != null && !licenseName.trim().isEmpty()) {
            this.licenseName = licenseName;
        }
    }

    public String getLicenseDescription() {
        return licenseDescription;
    }

    public void setLicenseDescription(String licenseDescription) {
        if (licenseDescription != null && !licenseDescription.trim().isEmpty()) {
            this.licenseDescription = licenseDescription;
        }
    }

    public Set<Provider> getProvidersWithLicense() {
        return providersWithLicense;
    }

    public void addProviderWithLicense(Provider provider) {
        if (provider != null) {
            providersWithLicense.add(provider);

            if (!provider.hasLicense(this)) {
                provider.addLicense(this);
            }
        }
    }

    public void removeProviderWithLicense(Provider provider) {
        if (provider != null && hasLicensedProvider(provider)) {
            providersWithLicense.remove(provider);

            if (provider.hasLicense(this)) {
                provider.removeLicense(this);
            }
        }
    }

    /**
     *
     * @param provider
     * @return
     */
    public boolean hasLicensedProvider(Provider provider) {
        return provider != null ? providersWithLicense.contains(provider) : false;
    }

    /** Comparator function to compare License objects
     *
     * @param o the Insurance object to compare to this object
     * @return true if the hash codes match
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof License) {
            License a = (License) o;

            return a.hashCode() == o.hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.licenseName != null ? this.licenseName.hashCode() : 0);
        hash = 97 * hash + (this.licenseDescription != null ? this.licenseDescription.hashCode() : 0);
        return hash;
    }
}
