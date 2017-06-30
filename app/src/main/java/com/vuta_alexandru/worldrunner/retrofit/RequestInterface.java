package com.vuta_alexandru.worldrunner.retrofit;

/**
 * Created by vuta on 26/04/2017.
 */

import com.vuta_alexandru.worldrunner.models.Authentication;
import com.vuta_alexandru.worldrunner.models.MyResponse;
import com.vuta_alexandru.worldrunner.models.User;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.ServerRequest;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.StepsRequest;
import com.vuta_alexandru.worldrunner.retrofit.request_beans.UpdateStepsReq;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.ServerResponse;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.StepsResponse;
import com.vuta_alexandru.worldrunner.retrofit.response_beans.UpdateStepsRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RequestInterface {

    @POST("authorization/login")
    Call<MyResponse<Authentication>> authentication(@Header("Authorization") String token);

    @POST("authorization/register")
    Call<MyResponse<Authentication>> registration(@Body User user);

    @GET("users/{id}")
    Call<MyResponse<User>> getUserById(@Header("Authorization") String token, @Path("id") int id);

    @POST("/")
    Call<ServerResponse> userReq(@Body ServerRequest request);

    @POST("/")
    Call<UpdateStepsRes> updateStepsRequest(@Body UpdateStepsReq request);


}