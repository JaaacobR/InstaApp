package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Post;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostInterface {
    @GET("api/photos")
    Call<List<Post>> getAllPosts();

    @Multipart
    @POST("api/photos")
    Call<Post> sendImage(
            @Part("album") RequestBody album,
            @Part MultipartBody.Part file
    );

}
