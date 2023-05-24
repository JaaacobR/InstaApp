package com.example.instaapp_client.api;

import com.example.instaapp_client.model.User;
import com.example.instaapp_client.requests.LoginRequest;
import com.example.instaapp_client.requests.RegisterRequest;
import com.example.instaapp_client.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInterface {

    @POST("/api/user/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest requestBody);

    @POST("/api/user/login")
    Call<User> loginUser(@Body LoginRequest requestBody);
}
