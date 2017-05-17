package com.vuta_alexandru.worldrunner.database_conn;

import com.vuta_alexandru.worldrunner.models.User;

/**
 * Created by vuta on 25/04/2017.
 */

public class ServerRequest {

    private String operation;
    private User user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }
}