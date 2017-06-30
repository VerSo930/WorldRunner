package com.vuta_alexandru.worldrunner.authorization;

import android.util.Log;

import com.vuta_alexandru.worldrunner.models.Authentication;
import com.vuta_alexandru.worldrunner.models.MyResponse;
import com.vuta_alexandru.worldrunner.retrofit.RequestInterface;
import com.vuta_alexandru.worldrunner.retrofit.RestClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by vuta on 30/06/2017.
 */

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request.Builder builder = request.newBuilder();
        builder.header("Accept", "application/json");


        // try the request
        Response response = chain.proceed(request);

        if (response.code() == 401) {

            // get a new token (I use a synchronous Retrofit call)
            synchronized (builder) {
                RequestInterface apiService = RestClient.getClient().create(RequestInterface.class);
                Call<MyResponse<Authentication>> call = apiService.authentication(AuthHelper.generateAuthorizationHeader("verso.930@gmail.com", "verso9394"));
                MyResponse<Authentication> myResponse = call.execute().body();

                //int code = myResponse.getCode() / 100;
                String token = null;
                token = myResponse.getData().getToken();

                Log.d("VTZ", "code: " + (myResponse.getCode()) + " message: " + myResponse.getMessage() + " toke: " + token);

                builder.header("Authorization", myResponse.getData().getToken());
                request = builder.build();

                return chain.proceed(request);

            }


        }


        // otherwise just pass the original response on
        return response;
    }

}