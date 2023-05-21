package com.example.instaapp_client.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp_client.model.User;
import com.example.instaapp_client.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterViewModel extends ViewModel {

    private MutableLiveData<User> mutableUser;

    public RegisterViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }

    public void registerUser() {
        Call<User> call = RetrofitService.getUserInterface().registerUser("sadfa", "sadfasdf", "sadfsdf", "dsafas");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mutableUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("fail", "fail" + call.toString() + " " + t.getMessage());
            }
        });
    }

    public MutableLiveData<User> getObservedUser(){
        return mutableUser;
    }
}
