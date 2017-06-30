package com.vuta_alexandru.worldrunner;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.vuta_alexandru.worldrunner.background_services.StepService;
import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.login_register.LoginFragment;
import com.vuta_alexandru.worldrunner.login_register.ProfileFragment;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    protected static MainActivity instance;
    private static SharedPreferences pref;
    StepService mBoundService;
    boolean mServiceBound = false;
    // Step Service callbacks
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService.MyBinder myBinder = (StepService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }
    };

    public static Resources getReso() {
        return instance.getResources();
    }

    public static SharedPreferences getSP() {
        return pref;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        initFragment();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, StepService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        mBoundService.saveValues();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mBoundService.saveValues();
        super.onDestroy();

    }

    // Init Fragments
    private void initFragment() {

        Fragment fragment;
        if (pref.getBoolean(Constants.IS_LOGGED_IN, false)) {
            fragment = new ProfileFragment();
        } else {
            fragment = new LoginFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
        /*
        Fragment fragment;
        fragment = new RegisterFragmentDetails();


        */
        // Log.d("vuta", pref.getString(Constants.NAME,"") +" uid:" +pref.getString(Constants.UNIQUE_ID,""));
        Log.d(Constants.TAG, "steps from prefs: " + pref.getInt(Constants.STEPS_NUMBER, 0));
    }
}
