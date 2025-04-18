package com.lumastech.ecoapp;


import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.RegisterRequest;
import com.lumastech.ecoapp.Models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    // LOG IN
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @POST("apilogin/login")
    Call<RegisterResponse> token(@Body RegisterRequest data);


    // VERIFY TOKEN
    @Headers({"Content-Type: application/json", "accept: application/json"})
    @GET("mobileapi/tokenverify")
    Call<ApiResponse> tokenVerify(@Header("Authorization") String token);

}