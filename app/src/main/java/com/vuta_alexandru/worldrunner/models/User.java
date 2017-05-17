package com.vuta_alexandru.worldrunner.models;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by vuta on 25/04/2017.
 */

public class User {

    private String name;
    private String email;
    private String unique_id;
    private String password;
    private String old_password;
    private String new_password;
    private String timezone;
    private String weight;
    private String steps;
    private String height;

    public User() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        this.timezone = tz.getID();
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTimezone() {
        return timezone;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}