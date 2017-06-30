package com.vuta_alexandru.worldrunner.authorization;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.vuta_alexandru.worldrunner.Tools.SpTools;
import com.vuta_alexandru.worldrunner.models.Authentication;
import com.vuta_alexandru.worldrunner.models.MyResponse;
import com.vuta_alexandru.worldrunner.models.User;
import com.vuta_alexandru.worldrunner.retrofit.RequestInterface;
import com.vuta_alexandru.worldrunner.retrofit.RestCallback;
import com.vuta_alexandru.worldrunner.retrofit.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vuta on 30/06/2017.
 */

public class RestController {

    private static RequestInterface apiService;

    public static void authentication(String email, String password, final RestCallback<MyResponse<Authentication>> callback, Context context) {
        apiService = RestClient.getClient().create(RequestInterface.class);
        Call<MyResponse<Authentication>> call = apiService.authentication(AuthHelper.generateAuthorizationHeader(email, password));
        call.enqueue(new Callback<MyResponse<Authentication>>() {
            @Override
            public void onResponse(Call<MyResponse<Authentication>> call, Response<MyResponse<Authentication>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MyResponse<Authentication>> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });

    }

    public static void registration(User user, final RestCallback<MyResponse<Authentication>> callback, Context context) {

        apiService = RestClient.getClient().create(RequestInterface.class);
        Call<MyResponse<Authentication>> call = apiService.registration(user);
        call.enqueue(new Callback<MyResponse<Authentication>>() {
            @Override
            public void onResponse(Call<MyResponse<Authentication>> call, Response<MyResponse<Authentication>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MyResponse<Authentication>> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public static void getUserById(int id, final RestCallback<MyResponse<User>> callback, Context context) {

        String token = SpTools.getToken(PreferenceManager.getDefaultSharedPreferences(context));
        Log.d("VTZ", "TOKEN : " + token);
        apiService = RestClient.getClient().create(RequestInterface.class);
        Call<MyResponse<User>> call = apiService.getUserById(token, id);
        call.enqueue(new Callback<MyResponse<User>>() {
            @Override
            public void onResponse(Call<MyResponse<User>> call, Response<MyResponse<User>> response) {
                Gson gson = new Gson();
                Log.d("VTZ", gson.toJson(response));
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MyResponse<User>> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });

    }


}
