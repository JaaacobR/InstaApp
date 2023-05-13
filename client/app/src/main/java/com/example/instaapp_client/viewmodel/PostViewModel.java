package com.example.instaapp_client.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.instaapp_client.model.Posts;
import com.example.instaapp_client.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel {

    private MutableLiveData<Posts> mutablePostsList;

    public PostViewModel(){
        this.mutablePostsList = new MutableLiveData<>();
    }

    public void getPosts() {
        Call<Posts> call = RetrofitService.getPostInterface().getAllPosts();

        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                mutablePostsList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.d("fail", "fail");
            }
        });
    }

    public MutableLiveData<Posts> getObservedPosts(){
        return mutablePostsList;
    }


}
