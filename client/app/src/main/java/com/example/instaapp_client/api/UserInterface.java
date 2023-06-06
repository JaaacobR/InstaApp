package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.model.User;
import com.example.instaapp_client.requests.LoginRequest;
import com.example.instaapp_client.requests.ProfileRequest;
import com.example.instaapp_client.requests.RegisterRequest;
import com.example.instaapp_client.responses.AuthResponse;
import com.example.instaapp_client.responses.RegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserInterface {

    @POST("/api/user/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest requestBody);

    @POST("/api/user/login")
    Call<User> loginUser(@Body LoginRequest requestBody);

    @GET("api/profile")
    Call<AuthResponse> getUser(@Header("Authorization") String token);
    @PATCH("/api/profile")
    Call<AuthResponse> update(@Header("Authorization") String Token, @Body ProfileRequest body);

    @Multipart
    @POST("api/profile")
    Call<AuthResponse> sendImage(
            @Header("Authorization") String Token,
            @Part("album") RequestBody album,
            @Part MultipartBody.Part file
    );
}
