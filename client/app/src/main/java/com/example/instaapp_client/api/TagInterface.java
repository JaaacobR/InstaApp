package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface TagInterface {
    @GET("/api/tags")
    Call<List<Tag>> getTags();

}
