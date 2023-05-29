package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instaapp_client.databinding.ActivityMainBinding;
import com.example.instaapp_client.databinding.ActivityPostBinding;
import com.example.instaapp_client.model.Post;

public class PostActivity extends AppCompatActivity {

    ActivityPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Post post = (Post) getIntent().getSerializableExtra("post");
    }
}