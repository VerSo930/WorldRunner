package com.vuta_alexandru.worldrunner.retrofit.request_beans;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.models.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by vuta on 19/05/2017.
 */

public class UpdateStepsReq {

    private String operation;
    private String datetime;
    private User user;

    public UpdateStepsReq(User usr) {
        operation = Constants.UPDATE_STEPS_OPERATION;
        user = usr;
        Calendar c = Calendar.getInstance();
        datetime = c.get(Calendar.HOUR_OF_DAY)+"";
    }




}
