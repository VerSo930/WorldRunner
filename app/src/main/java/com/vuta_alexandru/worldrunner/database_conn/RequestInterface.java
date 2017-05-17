package com.vuta_alexandru.worldrunner.database_conn;

/**
 * Created by vuta on 26/04/2017.
 */

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("/")
    Call<ServerResponse> userReq(@Body ServerRequest request);

    @POST("/")
    Call<StepsResponse> stepsReq(@Body StepsRequest request);

}