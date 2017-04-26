package com.vuta_alexandru.worldrunner.login_register;

/**
 * Created by vuta on 26/04/2017.
 */

import com.vuta_alexandru.worldrunner.models.ServerRequest;
import com.vuta_alexandru.worldrunner.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}