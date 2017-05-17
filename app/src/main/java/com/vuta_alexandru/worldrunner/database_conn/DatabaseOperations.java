package com.vuta_alexandru.worldrunner.database_conn;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ObjectConstructor;
import com.vuta_alexandru.worldrunner.login_register.Constants;
import com.vuta_alexandru.worldrunner.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vuta on 26/04/2017.
 * Database operation will manage all request send to Database
 * Create / update user (update password, update stats,
 */

public class DatabaseOperations {

    private Gson gson;
    private Retrofit retrofit;
    private RequestInterface requestInterface;


    /*** Constructor ***/
    public DatabaseOperations(Context ctx) {

        // Retrofit
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        requestInterface = retrofit.create(RequestInterface.class);
    }

    /**** Generalized request for all user operations
     ( register, login, update, getstats ) ****/
    public void sendUserReq(User user, String method, final DatabaseCallback cb) {

        // Initialize
       ServerRequest request = new ServerRequest();
        request.setOperation(method);
        request.setUser(user);
        Call<ServerResponse> resp = requestInterface.userReq(request);

        // Enqueue the request, execute
        resp.enqueue(new retrofit2.Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                cb.UpdateSuccess(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                cb.UpdateError(t.getLocalizedMessage());
            }
        });

    }

    public void getStepsReq(User user, String method, String interval, String limit, final DatabaseCallback cb) {

        // Initialize
        StepsRequest request = new StepsRequest();
        request.setOperation(method);
        request.setUser(user);
        request.setLimit(limit);
        request.setInterval(interval);
        Call<StepsResponse> resp = requestInterface.stepsReq(request);

        // Enqueue the request, execute
        resp.enqueue(new retrofit2.Callback<StepsResponse>() {
            @Override
            public void onResponse(Call<StepsResponse> call, retrofit2.Response<StepsResponse> response) {
                cb.UpdateSuccess(response.body().getSteps());
            }

            @Override
            public void onFailure(Call<StepsResponse> call, Throwable t) {
                cb.UpdateError(t.getLocalizedMessage());
            }
        });
    }


}
