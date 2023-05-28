package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.instaapp_client.databinding.ActivityMainBinding;
import com.example.instaapp_client.databinding.ActivityTagsBinding;
import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.model.Tag;
import com.example.instaapp_client.service.RetrofitService;
import com.google.android.material.chip.Chip;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagsActivity extends AppCompatActivity {

    ActivityTagsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Call<List<Tag>> call = RetrofitService.getTagInterface().getTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                for(int i=0; i < response.body().size(); i++){
                    Chip chip = new Chip(TagsActivity.this);

                    chip.setText(response.body().get(i).getTag());
                    binding.choiceGroup.addView(chip);
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.d("fail1234", t.getMessage());
                finish();

            }
        });
    }
}