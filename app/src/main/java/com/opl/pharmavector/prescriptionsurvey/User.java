package com.opl.pharmavector.prescriptionsurvey;

import com.bumptech.glide.util.Util;

/**
 * Model class.
 */
public class User {
    private String name;
    private String lastName;



    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return  name  ;
    }
}