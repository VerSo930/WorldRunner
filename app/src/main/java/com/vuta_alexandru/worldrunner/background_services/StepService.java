package com.vuta_alexandru.worldrunner.background_services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Binder;
import android.os.IBinder;

import android.preference.PreferenceManager;
import android.util.Log;

import com.vuta_alexandru.worldrunner.retrofit.DatabaseCallback;
import com.vuta_alexandru.worldrunner.retrofit.DatabaseOperations;
import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;


/**
 * Created by vuta on 24/04/2017.
 */

public class StepService extends Service implements StepCounter.Callback {
    public static Boolean serviceRunning = false;
    public StepCounter stp;
    SharedPreferences.Editor editor;
    private UpdateTimer timer;
    private SharedPreferences pref;
    private Calendar c;
    private int tmpSteps;
    private int lastHour;
    private JSONArray tmpHoursArray;

    private IBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        stp = new StepCounter(this, this);
        timer = new UpdateTimer();
        c = Calendar.getInstance();
        getValues();
        //initSPSteps();
        Log.d(Constants.TAG, "OnCreate");

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(Constants.TAG, "in onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(Constants.TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(Constants.TAG, "in onUnbind");
        return true;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "Onstartcommand");
        serviceRunning = true;
        return START_STICKY;
    }

    // TODO: Test hours object, write controller to reset object after updates
    @Override
    public void stepStarted(int steps) {
        tmpSteps = tmpSteps + 1;
        updateHoursJson(c.get(Calendar.HOUR_OF_DAY));
        Log.d(Constants.TAG, c.get(Calendar.HOUR_OF_DAY) + " STEPS OBJ =>" + tmpHoursArray.toString());

        if (timer.isTime()) {

            DatabaseOperations db = new DatabaseOperations(getApplication());
            User user = new User();
            //user.setUnique_id(pref.getString(Constants.UNIQUE_ID, ""));
            //user.setSteps(tmpSteps + "");

            DatabaseCallback cb = new DatabaseCallback() {
                @Override
                public void UpdateError(Object o) {
                    Log.d(Constants.TAG, " Request FAIL update steps from timer: " + o.toString());
                }

                @Override
                public void UpdateSuccess(Object o) {
                    Log.d(Constants.TAG, " Request OK update steps from timer: " + o.toString());
                    //update(tmpSteps);
                }
            };
            db.updateSteps(user, tmpHoursArray, cb);


        } else {
            //Log.d(Constants.TAG, "IS NOT YET THE TIME for update =>");
        }


    }

    public void update(int tmpSteps) {
        updateHoursJson(c.get(Calendar.HOUR_OF_DAY));
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, " OnDestroy Service");
        serviceRunning = false;
        //onCreate();
        //super.onDestroy();
        saveValues();
    }

    /**
     * Method to initialize Shared Preference object if not exist
     **/
    public void initSPSteps() {

        tmpHoursArray = new JSONArray();

        for (int i = 0; i < 24; i++) {

            try {
                tmpHoursArray.put(i, 0);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Put json into Shared Preferences
        updateSPString(Constants.STEPS_OBJECT, tmpHoursArray.toString());

    }

    /**
     * Add steps count for specified index ( hour )
     **/
    public void updateHoursJson(int hour) {

        // Add Value at specified hour
        try {
            tmpHoursArray.put(hour, (tmpHoursArray.getInt(hour) + 1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Set null of selected index ( hour )
     **/
    public void removeHoursJson(int hour) {
        try {
            tmpHoursArray.put(hour, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Write to Shared Pref
        updateSPString(Constants.STEPS_OBJECT, tmpHoursArray.toString());
        lastHour = c.get(Calendar.HOUR_OF_DAY);

    }

    /**
     * Reset values after update ( remove values from json object )
     **/
    public void resetVals() {
        updateSPInt(Constants.STEPS_NUMBER, 0);
        removeHoursJson(lastHour);
    }

    /**
     * Update Shared preferences by key with int type
     **/
    public void updateSPInt(String key, int value) {

        editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();

    }

    /**
     * Update Shared preferences by key with String type
     **/
    public void updateSPString(String key, String value) {

        editor = pref.edit();
        editor.putString(key, value);
        editor.apply();

    }

    /**
     * Save Values on Shared Preferences before stop service
     **/
    public void saveValues() {
        updateSPInt(Constants.STEPS_NUMBER, tmpSteps);
        updateSPString(Constants.STEPS_OBJECT, tmpHoursArray.toString());
        updateSPInt(Constants.LAST_STEPS_HOUR, lastHour);
        Log.d(Constants.TAG, "Save Values");
    }

    /**
     * Get Values from Shared Preferences on service start
     **/
    public void getValues() {
        // Try to get Array from shared preferences
        try {
            tmpHoursArray = new JSONArray(pref.getString(Constants.STEPS_OBJECT, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // If empty we will initialize
        if (tmpHoursArray == null) {
            tmpHoursArray = new JSONArray();
            initSPSteps();
            try {
                tmpHoursArray = new JSONArray(pref.getString(Constants.STEPS_OBJECT, null));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tmpSteps = pref.getInt(Constants.STEPS_NUMBER, 0);
        lastHour = pref.getInt(Constants.LAST_STEPS_HOUR, c.get(Calendar.HOUR_OF_DAY));
    }

    public class MyBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }
}