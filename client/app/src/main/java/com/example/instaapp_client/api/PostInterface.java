package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
    @GET("api/photos")
    Call<List<Post>> getAllPosts();
}
