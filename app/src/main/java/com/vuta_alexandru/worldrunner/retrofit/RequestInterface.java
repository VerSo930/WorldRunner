package com.vuta_alexandru.worldrunner.retrofit;

/**
 * Created by vuta on 26/04/2017.
 */

import com.vuta_alexandru.worldrunner.models.Authentication;
import com.vuta_alexandru.worldrunner.models.MyResponse;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.ServerRequest;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.StepsRequest;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.UpdateStepsReq;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.ServerResponse;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.StepsResponse;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.UpdateStepsRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("/authorization/login")
    Call<MyResponse<Authentication>> authentication();

    @POST("/")
    Call<StepsResponse> stepsReq(@Body StepsRequest request);

    @POST("/")
    Call<UpdateStepsRes> updateStepsRequest(@Body UpdateStepsReq request);


}