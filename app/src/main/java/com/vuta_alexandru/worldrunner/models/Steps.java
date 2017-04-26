package com.vuta_alexandru.worldrunner.models;

import java.sql.Time;
import java.sql.Timestamp;

import static com.vuta_alexandru.worldrunner.R.id.time;

/**
 * Created by vuta on 26/04/2017.
 */

public class Steps {
    private String unique_id;
    private int steps;
    private Timestamp tsTemp;
    public Steps() {
        this.tsTemp = new Timestamp(time);

    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
