package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.model.Tag;
import com.example.instaapp_client.requests.RequestTags;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;


public interface TagInterface {
    @GET("/api/tags")
    Call<List<Tag>> getTags();

    @PATCH("api/photos/tags/mass")
    Call<Post> setTags(@Body RequestTags requestBody);


}
