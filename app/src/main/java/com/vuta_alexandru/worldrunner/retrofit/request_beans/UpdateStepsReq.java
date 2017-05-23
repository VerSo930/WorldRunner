package com.vuta_alexandru.worldrunner.retrofit.request_beans;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vuta on 19/05/2017.
 */

public class UpdateStepsReq {

    private String operation;
    private Map steps;
    private String date;
    private User user;

    public UpdateStepsReq(User usr, JSONArray Object) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        operation = Constants.UPDATE_STEPS_OPERATION;
        user = usr;

        String curdate = format1.format(c.getTime());

        List<String> obj = new ArrayList<>();
        steps = new HashMap<>();

        for (int i = 0; i < Object.length(); i++) {
            try {
                obj.add(Object.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        steps.put("date", curdate);
        steps.put("values", obj);

    }




}
