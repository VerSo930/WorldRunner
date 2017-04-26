package com.vuta_alexandru.worldrunner.background_services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.vuta_alexandru.worldrunner.login_register.Constants;

/**
 * Created by vuta on 24/04/2017.
 */

public class StepService extends Service implements StepCounter.Callback {
    public StepCounter stp;
    private SharedPreferences pref;


    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StepService.this.getSharedPreferences("", Context.MODE_PRIVATE);
        pref = PreferenceManager.getDefaultSharedPreferences(StepService.this);
        stp = new StepCounter(this , this);

        //TODO do something useful
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void stepStarted(int steps) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Constants.STEPS_NUMBER, steps);
        editor.apply();
        Log.d(Constants.TAG, "STEP SERVICE:From shared prefs:" + pref.getInt(Constants.STEPS_NUMBER, 0));
    }
}