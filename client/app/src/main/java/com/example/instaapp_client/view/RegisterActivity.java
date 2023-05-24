package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.ActivityLoginBinding;
import com.example.instaapp_client.databinding.ActivityRegisterBinding;
import com.example.instaapp_client.viewmodel.PostViewModel;
import com.example.instaapp_client.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.setRegisterViewModel(registerViewModel);

        binding.registerBtn.setOnClickListener(v -> {
            registerViewModel.registerUser(binding.login.getText().toString(), binding.email.getText().toString(), binding.fullName.getText().toString(),binding.password.getText().toString());
        });

        registerViewModel.getObservedUser().observe(RegisterActivity.this, s -> {
            binding.setRegisterViewModel(registerViewModel);
        });



    }
}