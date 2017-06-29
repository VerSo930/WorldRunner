package com.vuta_alexandru.worldrunner.authorization;

import android.util.Base64;

/**
 * Created by vuta on 29/06/2017.
 */

public class AuthHelper {

    public static String generateAuthorizationHeader(String user, String password) {
        String encodedUsernameAndPassword = user + ":" + password;
        byte[] byts = encodedUsernameAndPassword.getBytes();
        return "Basic " + Base64.encodeToString(byts, Base64.DEFAULT);
    }
}
