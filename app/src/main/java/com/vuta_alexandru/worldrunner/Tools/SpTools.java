package com.vuta_alexandru.worldrunner.Tools;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.vuta_alexandru.worldrunner.MainActivity;
import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.models.User;

/**
 * Created by vuta on 30/06/2017.
 */

public class SpTools {

    private static Gson gson = new Gson();

    public static void saveUser(SharedPreferences mPrefs, User user) {
        String json = gson.toJson(user);
        mPrefs.edit().putString(Constants.SP_USER, json).commit();
    }

    public static User getUser(SharedPreferences mPrefs) {
        String json = mPrefs.getString(Constants.SP_USER, null);
        return gson.fromJson(json, User.class);
    }

    public static void saveToken(SharedPreferences mPrefs, String token) {
        mPrefs.edit().putString(Constants.SP_TOKEN, token).commit();
    }

    public static String getToken(SharedPreferences mPrefs) {
        return mPrefs.getString(Constants.SP_TOKEN, null);
    }

    public static String getAuthToken() {
        return MainActivity.getSP().getString(Constants.SP_USER, null);

    }

    public static void saveAuthToken(String authToken) {
        MainActivity.getSP().edit().putString(Constants.SP_USER, authToken).commit();
    }


}
