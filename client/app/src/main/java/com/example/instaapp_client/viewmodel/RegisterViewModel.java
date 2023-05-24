package com.example.instaapp_client.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp_client.model.User;
import com.example.instaapp_client.requests.RegisterRequest;
import com.example.instaapp_client.responses.RegisterResponse;
import com.example.instaapp_client.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterResponse> mutableUser;

    public RegisterViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }

    public void registerUser(String login, String email, String fullName, String password) {
        Call<RegisterResponse> call = RetrofitService.getUserInterface().registerUser(new RegisterRequest(login, fullName, email, password));

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("token" , response.body().toString());
                mutableUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("fail", "fail" + call.toString() + " " + t.getMessage());
            }
        });
    }

    public MutableLiveData<RegisterResponse> getObservedUser(){
        return mutableUser;
    }
}
