package com.vuta_alexandru.worldrunner.background_services;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.vuta_alexandru.worldrunner.login_register.Constants;

/**
 * Created by Vuta Alexandru on 4/23/2017.
 */

public class StepCounter {
    private SensorManager mgr=null;
    private Sensor step;
    public Callback cb=null;
    private int nbSteps;

    public int getNbSteps() {
        return nbSteps;
    }

    public StepCounter(Context ctx, Callback cb) {
        this.cb = cb;
        mgr = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        step = mgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mgr.registerListener(listener, step, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(Constants.TAG, "Object Created ");

     }
     private SensorEventListener listener = new SensorEventListener() {
         @Override
         public void onSensorChanged(SensorEvent event) {
             //Log.d(Constants.TAG, "Sensor changed :"+ event.values[0]);
             nbSteps = (int) event.values[0];
             cb.stepStarted(nbSteps);
         }

         @Override
         public void onAccuracyChanged(Sensor sensor, int accuracy) {
         }
     } ;
     public void close() {
         mgr.unregisterListener(listener);
         nbSteps = 0;
         Log.d(Constants.TAG, "Object Closed! ");
     }
    public void restart() {
        mgr.registerListener(listener, step, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(Constants.TAG, "Object Restart");
    }

    public interface Callback {
        void stepStarted(int steps);
    }

}
