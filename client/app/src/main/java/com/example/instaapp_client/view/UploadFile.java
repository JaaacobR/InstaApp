package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.ActivityUploadFileBinding;

import com.example.instaapp_client.requests.FilterRequest;

import com.example.instaapp_client.store.Store;
import com.example.instaapp_client.viewmodel.UploadFileViewModel;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadFile extends AppCompatActivity {

    ActivityUploadFileBinding binding;
    List<String> tagsList = new ArrayList<String>();
    private UploadFileViewModel uploadViewModel;
    private ImageView imageView;

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void openModal(){
        final Dialog dialog = new Dialog(UploadFile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(1000, 1000);
        dialog.setContentView(R.layout.dialog_filter_layout);

        Button cancelButton = dialog.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener( v -> {
            dialog.dismiss();
        });

        Button grayScaleBtn = dialog.findViewById(R.id.grayScaleBtn);
        grayScaleBtn.setOnClickListener(v -> {
            //code
            FilterRequest filterRequest = new FilterRequest(uploadViewModel.getObservedPosts().getValue().getId(), uploadViewModel.getObservedPosts().getValue().getUrl(), "grayscale", null);
            uploadViewModel.setFilter(filterRequest);

            dialog.dismiss();
        });

        Button flipBtn = dialog.findViewById(R.id.flipBtn);
        flipBtn.setOnClickListener(v -> {
            FilterRequest filterRequest = new FilterRequest(uploadViewModel.getObservedPosts().getValue().getId(), uploadViewModel.getObservedPosts().getValue().getUrl(), "flop", null);
            uploadViewModel.setFilter(filterRequest);
            dialog.dismiss();
        });

        Button negateBtn = dialog.findViewById(R.id.negateBtn);
        negateBtn.setOnClickListener(v -> {
            FilterRequest filterRequest = new FilterRequest(uploadViewModel.getObservedPosts().getValue().getId(), uploadViewModel.getObservedPosts().getValue().getUrl(), "negate", null);
            uploadViewModel.setFilter(filterRequest);
            dialog.dismiss();
        });

        dialog.show();
    }

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

        uploadViewModel = new ViewModelProvider(this).get(UploadFileViewModel.class);

        uploadViewModel.getObservedPosts().observe(this , s -> {
            if(uploadViewModel.getObservedPosts().getValue() != null){
                Log.d("XXXX", uploadViewModel.getObservedPosts().getValue().toString());
                binding.linearButtons.removeView(binding.uploadBtn);

                Button btnLocation = new Button(UploadFile.this);
                btnLocation.setText("Place");
                btnLocation.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                if(binding.linearButtons.getChildCount() != 4) {
                    binding.linearButtons.addView(btnLocation);
                }
                btnLocation.setOnClickListener( v -> {
                    Intent intent = new Intent(UploadFile.this, MapActivity.class);
                    intent.putExtra("id", String.valueOf(uploadViewModel.getObservedPosts().getValue().getId()));
                    startActivity(intent);
                });

                Button btnTags = new Button(UploadFile.this);
                btnTags.setText("Tags");
                btnTags.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                if(binding.linearButtons.getChildCount() != 4) {
                    binding.linearButtons.addView(btnTags);
                }
                btnTags.setOnClickListener(v -> {
                    List<String> tagsList = new ArrayList<>();
                    tagsList.clear();
                    for (int i = 0; i < uploadViewModel.getObservedPosts().getValue().getTags().size(); i++) {
                        tagsList.add(uploadViewModel.getObservedPosts().getValue().getTags().get(i).getTag());
                    }
                    Intent intent = new Intent(UploadFile.this, TagsActivity.class);
                    intent.putExtra("id", String.valueOf(uploadViewModel.getObservedPosts().getValue().getId()));
                    intent.putExtra("tagList", (ArrayList<String>) tagsList);
                    startActivity(intent);
                });

                if (type.equals("image")) {
                    String status = uploadViewModel.getObservedPosts().getValue().getHistory().get(uploadViewModel.getObservedPosts().getValue().getHistory().size()-1).getStatus();
                    if(status != null || status != "original"){
                        Glide.with(imageView.getContext()).load("http://192.168.119.67:3000/api/getfile/" + uploadViewModel.getObservedPosts().getValue().getId() + "/" + status).into(imageView);
                    }
                    Button btnFilters = new Button(UploadFile.this);
                    btnFilters.setText("Filters");
                    btnFilters.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    if(binding.linearButtons.getChildCount() != 4){
                        binding.linearButtons.addView(btnFilters);
                    }

                    btnFilters.setOnClickListener( v -> {
                        openModal();
                    });
                }
            }
        });



        if (type.equals("image")) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            binding.linearLayout.addView(imageView);

            Glide.with(imageView.getContext()).load(uri).into(imageView);
        } else {
            VideoView videoView = new VideoView(this);
            videoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            binding.linearLayout.addView(videoView);

            videoView.setVideoURI(Uri.parse(uri));
            MediaController mediaController = new MediaController(this);

            mediaController.setAnchorView(videoView);
            mediaController.setMediaPlayer(videoView);
            videoView.setMediaController(mediaController);
            videoView.start();
        }

        binding.goBack.setOnClickListener(v -> {
            finish();
        });


        binding.uploadBtn.setOnClickListener(v -> {
            File file = new File(type.equals("video") ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/" + timestamp + ".mp4" : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + timestamp + ".jpg");
            RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
            RequestBody album = RequestBody.create(MultipartBody.FORM, Store.getLogin());

            uploadViewModel.sendPost(album, body);
        });
    }
}