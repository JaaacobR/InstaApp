package com.example.instaapp_client.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.requests.ProfileRequest;
import com.example.instaapp_client.requests.RegisterRequest;
import com.example.instaapp_client.responses.AuthResponse;
import com.example.instaapp_client.responses.RegisterResponse;
import com.example.instaapp_client.service.RetrofitService;
import com.example.instaapp_client.store.Store;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<AuthResponse> mutableUser;

    public ProfileViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }

    public void updateProfile(ProfileRequest profileRequest){
        Call<AuthResponse> call = RetrofitService.getUserInterface().update("Bearer " + Store.getToken(), profileRequest);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                Log.d("token123" , response.body().toString());

                mutableUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

            }
        });
    }

    public void sendPost(RequestBody album, MultipartBody.Part body) {
        Call<AuthResponse> call = RetrofitService.getUserInterface().sendImage("Bearer " + Store.getToken(), album, body);


        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                Log.d("success123", response.body().toString());
                mutableUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.d("fail", "fail" + call.toString() + " " + t.getMessage());
            }
        });
    }
    public void getUser() {
        Log.d("1234567890", Store.getToken());
        Call<AuthResponse> call = RetrofitService.getUserInterface().getUser("Bearer " + Store.getToken());

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                Log.d("token123" , response.body().toString());

                mutableUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<AuthResponse> getObservedUser(){
        return mutableUser;
    }
}