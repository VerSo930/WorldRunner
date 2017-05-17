package com.vuta_alexandru.worldrunner.models;

/**
 * Created by vuta on 17/05/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("steps")
    @Expose
    private String steps;
    @SerializedName("lastupdate")
    @Expose
    private String lastupdate;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}