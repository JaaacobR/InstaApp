package com.example.instaapp_client.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.model.Posts;
import com.example.instaapp_client.service.RetrofitService;
import com.example.instaapp_client.view.Home;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {

    private MutableLiveData<List<Post>> mutablePostsList;

    public PostViewModel(){
        this.mutablePostsList = new MutableLiveData<>();
    }

    public void getPosts() {
        Call<List<Post>> call = RetrofitService.getPostInterface().getAllPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("data123" , response.body().get(1).toString());
                mutablePostsList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("fail", "fail" + call.toString() + " " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<Post>> getObservedPosts(){
        return mutablePostsList;
    }


}
