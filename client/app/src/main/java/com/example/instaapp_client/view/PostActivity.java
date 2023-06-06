package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.instaapp_client.databinding.ActivityMainBinding;
import com.example.instaapp_client.databinding.ActivityPostBinding;
import com.example.instaapp_client.model.Post;

public class PostActivity extends AppCompatActivity {

    private ActivityPostBinding binding;
    private boolean isVideo = true;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        Post post = (Post) getIntent().getSerializableExtra("post");
        if(post.getLocation() == null){
            binding.placeBtn.setEnabled(false);
        }
        Log.d("XXXXX", String.valueOf(post.getUrl().charAt(post.getUrl().lastIndexOf("."))));
        if(post.getUrl().charAt(post.getUrl().lastIndexOf(".") + 1) == 'j'){
            isVideo = false;
        }

        String tags = "";
        for(int i =0; i < post.getTags().size() - 1; i++){
            tags = tags + " " + post.getTags().get(i).getTag();
        }
        binding.tags.setText(tags);

        binding.placeBtn.setOnClickListener( v -> {
            Intent intent = new Intent(this , MapViewActivity.class);
            intent.putExtra("location", post.getLocation());
            startActivity(intent);
        });



        if(isVideo){
            player = new ExoPlayer.Builder(this).build();
            PlayerView playerView = new PlayerView(this);
            binding.mediaLayout.addView(playerView);
            playerView.setPlayer(player);
            MediaItem mediaItem = MediaItem.fromUri("http://192.168.119.67:3000/api/getfile/" + post.getId());
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();

        }else{
            ImageView imageView = new ImageView(this);
            binding.mediaLayout.addView(imageView);
            String status = post.getHistory().get(post.getHistory().size()-1).getStatus();
            String toUrl = "";
            if(status != null && status != "original"){
                toUrl += "/" + status;
            }

            Glide.with(imageView.getContext()).load("http://192.168.119.67:3000/api/getfile/" + post.getId()+ toUrl).into(imageView);
        }





    }
}