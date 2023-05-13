package com.example.instaapp_client.service;

import com.example.instaapp_client.api.PostInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String BASE_URL = "http://localhost/api";

    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    public static PostInterface getPostInterface()
    {
        return retrofit.create(PostInterface.class);
    }}
