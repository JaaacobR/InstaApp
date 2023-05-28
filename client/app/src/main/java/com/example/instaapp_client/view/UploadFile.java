package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.instaapp_client.databinding.ActivityUploadFileBinding;
import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.model.User;
import com.example.instaapp_client.requests.LoginRequest;
import com.example.instaapp_client.service.RetrofitService;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFile extends AppCompatActivity {

    ActivityUploadFileBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadFileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();
        String uri = bundle.getString("uri");
        String type = bundle.getString("type");
        String timestamp = bundle.getString("timestamp");

        if(type.equals("image")){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            binding.linearLayout.addView(imageView);

            Glide.with(imageView.getContext()).load(uri).into(imageView);
        }else{
            VideoView videoView = new VideoView(this);
            videoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            binding.linearLayout.addView(videoView);

           videoView.setVideoURI(Uri.parse(uri));
            MediaController mediaController = new MediaController(this);

            // sets the anchor view
            // anchor view for the videoView
            mediaController.setAnchorView(videoView);

            // sets the media player to the videoView
            mediaController.setMediaPlayer(videoView);

            // sets the media controller to the videoView
            videoView.setMediaController(mediaController);

            // starts the video
            videoView.start();
        }

        binding.goBack.setOnClickListener(v -> {
            finish();
        });


        binding.uploadBtn.setOnClickListener(v -> {
            File file = new File(type.equals("video") ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/" + timestamp + ".mp4" : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + timestamp + ".jpg");
            Log.d("fail" , uri);
            RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
            RequestBody album = RequestBody.create(MultipartBody.FORM, "samochody");

            Call<Post> call = RetrofitService.getPostInterface().sendImage(album, body);

            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    binding.linearButtons.removeView(binding.uploadBtn);
                    Button btnTags = new Button(UploadFile.this);
                    btnTags.setText("Tags");
                    btnTags.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    binding.linearButtons.addView(btnTags);
                    btnTags.setOnClickListener( v -> {
                        Log.d("infp123", String.valueOf(response.body().getId()));
                        Intent intent = new Intent(UploadFile.this, TagsActivity.class);
                        intent.putExtra("id", String.valueOf(response.body().getId()));
                        startActivity(intent);
                    });

                   if(type.equals("image")){
                       Button btnFilters = new Button(UploadFile.this);
                       btnFilters.setText("Filters");
                       btnFilters.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                       binding.linearButtons.addView(btnFilters);
                   }

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