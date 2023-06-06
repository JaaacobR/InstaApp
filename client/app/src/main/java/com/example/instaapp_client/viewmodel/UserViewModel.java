package com.example.instaapp_client.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp_client.model.User;
import com.example.instaapp_client.requests.LoginRequest;
import com.example.instaapp_client.requests.RegisterRequest;
import com.example.instaapp_client.responses.RegisterResponse;
import com.example.instaapp_client.service.RetrofitService;
import com.example.instaapp_client.view.ProfileFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> mutableUser;

    public UserViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }

    public void loginUser(String login, String password) {
        Call<User> call = RetrofitService.getUserInterface().loginUser(new LoginRequest(login,password));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                mutableUser.setValue(response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("fail", "failKurwa" + call.toString() + " " + t.getMessage());
            }
        });
    }

    public MutableLiveData<User> getObservedUser(){
        return mutableUser;
    }
}
