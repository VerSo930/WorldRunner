package com.vuta_alexandru.worldrunner.login_register;

/**
 * Created by vuta on 25/04/2017.
 */

public class Constants {

    public static final String BASE_URL = "https://vuta-alexandru.com:8443/WorldRunner/v1/";
    public static final String REGISTER_OPERATION = "register";
    public static final String LOGIN_OPERATION = "login";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";
    public static final String UPDATE_STEPS_OPERATION = "updsteps";
    public static final String GET_STEPS_OPERATION = "getsteps";
    public static final String UPDATE_USER_OPERATION = "upduser";

    public static final String SP_USER = "sharedpreference_user";
    public static final String SP_TOKEN = "sharedpreference_token";
    public static final String SP_AUTH_TOKEN = "sharedpreference_auth_token";

    public static final long UPDATE_STEPS_INTERVAL =   60 * 1000;

    public static final String SUCCESS = "success";

    public static final String FAILURE = "failure";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String UNIQUE_ID = "unique_id";
    public static final String STEPS_NUMBER = "steps_number";
    public static final String STEPS_OBJECT = "steps_object";
    public static final String LAST_STEPS_HOUR = "last_steps_object";

    public static final String SERVER_LAST_STEPS = "server_last_steps";
    public static final String SERVER_LAST_STEPS_UPDATE = "server_last_steps_update";

    public static final String TAG = "WorldRunner";

}