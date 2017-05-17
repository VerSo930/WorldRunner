package com.vuta_alexandru.worldrunner.background_services;

import com.vuta_alexandru.worldrunner.login_register.Constants;

import java.util.Calendar;

/**
 * Created by vuta on 11/05/2017.
 */

public class UpdateTimer {

    private Calendar currentDate;
    private long startMillis;
    private boolean firstRun;

    UpdateTimer() {
        firstRun = true;
        this.currentDate = Calendar.getInstance();
        this.startMillis = currentDate.getTimeInMillis();
    }

    public boolean isTime () {
        this.currentDate = Calendar.getInstance();
        long diff = currentDate.getTimeInMillis() - startMillis;
        if(diff >= Constants.UPDATE_STEPS_INTERVAL || firstRun) {
            this.startMillis = currentDate.getTimeInMillis();
            firstRun = false;
            return true;
        } else {
            return  false;
        }

    }
}
