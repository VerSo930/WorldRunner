package com.vuta_alexandru.worldrunner.models;

/**
 * Created by vuta on 29/06/2017.
 */

public class Authentication {

    private String token;
    private User user;

    public Authentication() {
    }

    public Authentication( String token, User user) {

        this.token = token;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}