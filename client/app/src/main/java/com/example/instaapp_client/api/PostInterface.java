package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Post;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
    @GET("getAll")
    Call<Post> getAllPosts();
}
