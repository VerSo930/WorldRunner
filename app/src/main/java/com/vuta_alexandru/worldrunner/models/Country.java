package com.vuta_alexandru.worldrunner.models;

/**
 * Created by vuta on 27/04/2017.
 */

public class Country {

    private String id;
    private String name;
    private String alpha_2;

    public String getAlpha_2() {
        return alpha_2;
    }

    public void setAlpha_2(String alpha_2) {
        this.alpha_2 = alpha_2;
    }

    public Country(String id, String name, String alpha_2) {
        this.id = id;
        this.name = name;
        this.alpha_2 = alpha_2;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Country){
            Country c = (Country )obj;
            if(c.getName().equals(name) && c.getId()==id ) return true;
        }

        return false;
    }

}