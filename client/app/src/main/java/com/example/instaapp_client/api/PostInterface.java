package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Posts;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
    @GET("getAll")
    Call<Posts> getAllPosts();
}
