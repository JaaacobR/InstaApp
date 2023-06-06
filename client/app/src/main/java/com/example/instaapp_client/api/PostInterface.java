package com.example.instaapp_client.api;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.requests.FilterRequest;
import com.example.instaapp_client.requests.LocationRequest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostInterface {
    @GET("api/photos")
    Call<List<Post>> getAllPosts();

    @Multipart
    @POST("api/photos")
    Call<Post> sendImage(
            @Part("album") RequestBody album,
            @Part MultipartBody.Part file
    );

    @PATCH("api/filters")
    Call<Post> addFilter(@Body FilterRequest requestBody);

    @GET("api/photos/{id}")
    Call<Post> getPhoto(@Path("id") String id);

    @PATCH("/api/photos/location")
    Call<Post> setLocation(@Body LocationRequest requestBody);

}
