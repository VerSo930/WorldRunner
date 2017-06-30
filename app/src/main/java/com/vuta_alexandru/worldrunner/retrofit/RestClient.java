package com.vuta_alexandru.worldrunner.retrofit;

import android.content.Context;

import com.vuta_alexandru.worldrunner.authorization.AuthHelper;
import com.vuta_alexandru.worldrunner.authorization.AuthInterceptor;
import com.vuta_alexandru.worldrunner.login_register.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vuta on 30/06/2017.
 */

public class RestClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(AuthHelper.createClient().build())
                    .build();
        }
        return retrofit;
    }
}
