package com.vuta_alexandru.worldrunner.database_conn;

import java.util.Objects;

/**
 * Created by vuta on 11/05/2017.
 */

public interface DatabaseCallback {
    void UpdateError(Object o);
    void UpdateSuccess(Object o);
}