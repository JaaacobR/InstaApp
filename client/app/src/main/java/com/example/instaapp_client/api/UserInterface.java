package com.example.instaapp_client.api;

import com.example.instaapp_client.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInterface {
    @FormUrlEncoded
    @POST("/api/user/register")
    Call<User> registerUser(
            @Field("login") String login,
            @Field("email") String email,
            @Field("fullName") String fullName,
            @Field("password") String password
    );
}
