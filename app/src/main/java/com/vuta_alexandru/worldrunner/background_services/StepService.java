package com.vuta_alexandru.worldrunner.background_services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.vuta_alexandru.worldrunner.database_conn.DatabaseCallback;
import com.vuta_alexandru.worldrunner.database_conn.DatabaseOperations;
import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.models.User;

/**
 * Created by vuta on 24/04/2017.
 */

public class StepService extends Service implements StepCounter.Callback {

    public StepCounter stp;
    private UpdateTimer timer;
    private SharedPreferences pref;
    private DatabaseCallback cb;

    @Override
    public void onCreate() {
        super.onCreate();
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        stp = new StepCounter(this , this);
        timer = new UpdateTimer();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void stepStarted(int steps) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Constants.STEPS_NUMBER, steps);
        editor.apply();
        if (timer.isTime()){
            Log.d(Constants.TAG, "IS time for update =>");
            DatabaseOperations db = new DatabaseOperations(getApplication());
            this.cb = new DatabaseCallback() {
                @Override
                public void UpdateError(Object o) {
                    Log.d(Constants.TAG, " Request FAIL update steps from timer: "+o.toString());
                }

                @Override
                public void UpdateSuccess(Object o) {
                    Log.d(Constants.TAG, " Request OK update steps from timer: "+o.toString());
                }
            };

            User user = new User();
            user.setUnique_id(pref.getString(Constants.UNIQUE_ID,""));
            user.setSteps(steps+"");

            db.sendUserReq(user, Constants. UPDATE_STEPS_OPERATION, cb);
        } else {
            Log.d(Constants.TAG, "IS NOT YET THE TIME for update =>");
        }
        Log.d(Constants.TAG, "STEP SERVICE:From shared prefs:" + pref.getInt(Constants.STEPS_NUMBER, 0));


    }
}