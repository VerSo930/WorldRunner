package com.vuta_alexandru.worldrunner.background_services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vuta on 16/05/2017.
 */

public class StartServiceOnBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, StepService.class);
        context.startService(myIntent);

    }
}