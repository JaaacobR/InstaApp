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
import com.example.instaapp_client.requests.RequestTags;
import com.example.instaapp_client.service.RetrofitService;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
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

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        ArrayList<String> tagsList = bundle.getStringArrayList("tagList");

        binding.choiceGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {

            Log.d("xxx", "checkedIds: " + checkedIds);

            for (int i = 0; i < group.getChildCount(); i++) {
                Chip chip = (Chip) group.getChildAt(i);
                chip.setOnCheckedChangeListener((compoundButton, b) -> {
                    Log.d("xxx", "change");
                });
                if (chip.isChecked()) {
                    chip.setBackgroundColor(0xFF00FF);
                }
            }

        });

        Call<List<Tag>> call = RetrofitService.getTagInterface().getTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                for(int i=0; i < response.body().size(); i++){
                    Chip chip = new Chip(TagsActivity.this);
                    chip.setCheckable(true);

                    chip.setText(response.body().get(i).getTag());
                    Log.d("taglist", tagsList.toString());

                    if(tagsList.contains(response.body().get(i).getTag())){
                        chip.setChecked(true);
                    }

                    binding.choiceGroup.addView(chip);
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.d("fail1234", t.getMessage());
                finish();

            }
        });

        binding.addTagBtn.setOnClickListener( v -> {
            List<Tag> tags = new ArrayList<Tag>();
            for (int i = 0; i < binding.choiceGroup.getChildCount(); i++) {

                Chip chip = (Chip) binding.choiceGroup.getChildAt(i);
                if(chip.isChecked()){
                    tags.add(new Tag(chip.getId(), chip.getText().toString(), 10000));
                }

            }

            Log.d("ID123", id);

            Call<Post> patchCall = RetrofitService.getTagInterface().setTags(new RequestTags(Long.parseLong(id), tags));

            patchCall.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Log.d("fail1234", t.getMessage());
                    finish();

                }
            });

        });


    }
}