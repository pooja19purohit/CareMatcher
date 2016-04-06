/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.business;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** Creates a Remembered User entity table to store remembered usernames & cookie validation
 *
 * @author Kevin Buckley
 */
@Entity
@Table(name="REMEMBERED_USER")
public class RememberedUser implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String username;
    private String series;
    private String token;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username;
        }
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        if (series != null && !series.trim().isEmpty()) {
            this.series = series;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token != null && !token.trim().isEmpty()) {
            this.token = token;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RememberedUser) {
            RememberedUser a = (RememberedUser) o;

            return a.hashCode() == o.hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 97 * hash + (this.token != null ? this.token.hashCode() : 0);
        return hash;
    }
}
