package com.vuta_alexandru.worldrunner.background_services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vuta_alexandru.worldrunner.login_register.Constants;

/**
 * Created by vuta on 26/04/2017.
 */

public class UpdateUser {
    public static int user_unique_id;
    private static SharedPreferences pref;
    private Callback cb;

    public UpdateUser(Context ctx) {
        pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        if(!pref.contains(Constants.UNIQUE_ID)) {
            this.user_unique_id = pref.getInt(Constants.UNIQUE_ID, 0);
        } else {
            cb.UpdateError("User id not set!");
        }
    }

    public interface Callback {
        void UpdateError(String message);
    }
}
