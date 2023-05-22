package com.example.instaapp_client.api;

import com.example.instaapp_client.model.User;
import com.example.instaapp_client.requests.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInterface {

    @POST("/api/user/register")
    Call<User> registerUser(@Body RegisterRequest requestBody);
}
