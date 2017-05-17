package com.vuta_alexandru.worldrunner.database_conn;

/**
 * Created by vuta on 17/05/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vuta_alexandru.worldrunner.models.Step;

import java.util.List;

public class StepsResponse {

    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private String result;

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}