package com.vuta_alexandru.worldrunner.retrofit.response_beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vuta_alexandru.worldrunner.models.Step;

import java.util.List;

/**
 * Created by vuta on 19/05/2017.
 */

public class UpdateStepsRes {


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
