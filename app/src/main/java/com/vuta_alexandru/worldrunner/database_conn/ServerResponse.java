package com.vuta_alexandru.worldrunner.database_conn;

import com.vuta_alexandru.worldrunner.models.User;

/**
 * Created by vuta on 25/04/2017.
 */

public class ServerResponse {

    private String result;
    private String message;
    private User user;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}