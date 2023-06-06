package com.example.instaapp_client.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.requests.FilterRequest;
import com.example.instaapp_client.service.RetrofitService;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFileViewModel extends ViewModel {
    private MutableLiveData<Post> mutablePostsList;

    public UploadFileViewModel(){
        this.mutablePostsList = new MutableLiveData<>();
    }

    public void setFilter(FilterRequest filterRequest){
        Call<Post> call = RetrofitService.getPostInterface().addFilter(filterRequest);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d("XXXXX", response.body().toString());
                mutablePostsList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("XXXXX" , t.getMessage());
            }
        });
    }

    public void sendPost(RequestBody album, MultipartBody.Part body) {
        Call<Post> call = RetrofitService.getPostInterface().sendImage(album, body);


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                mutablePostsList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("fail", "fail" + call.toString() + " " + t.getMessage());
            }
        });
    }

    public MutableLiveData<Post> getObservedPosts(){
        return mutablePostsList;
    }
}
