package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.ActivityLoginBinding;
import com.example.instaapp_client.databinding.ActivityMainBinding;
import com.example.instaapp_client.viewmodel.RegisterViewModel;
import com.example.instaapp_client.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.setUserViewModel(userViewModel);

        binding.registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.loginBtn.setOnClickListener(v -> {
            userViewModel.loginUser(binding.login.getText().toString(), binding.password.getText().toString());
        });

        userViewModel.getObservedUser().observe(LoginActivity.this, s -> {
            if(userViewModel.getObservedUser().getValue().getToken() != null){
                
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}