package com.vuta_alexandru.worldrunner;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.vuta_alexandru.worldrunner.background_services.StepService;
import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.login_register.LoginFragment;
import com.vuta_alexandru.worldrunner.login_register.ProfileFragment;
import com.vuta_alexandru.worldrunner.login_register.RegisterFragmentDetails;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar c = Calendar.getInstance();

        String sDate = c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.MONTH)
                + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " at " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);

        Log.d(Constants.TAG, "Date: "+sDate);


        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        initFragment();
        // Start Step counter sensor service
        Intent i = new Intent(MainActivity.this, StepService.class);
        this.startService(i);

    }

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
        Log.d("vuta", "steps from prefs: " + pref.getInt(Constants.STEPS_NUMBER, 0));
    }

}
