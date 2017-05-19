package com.vuta_alexandru.worldrunner.retrofit.request_beans;

import com.vuta_alexandru.worldrunner.models.User;

/**
 * Created by vuta on 17/05/2017.
 */

public class StepsRequest {

    private String operation;
    private String limit;
    private String interval;
    private User user;

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }
}