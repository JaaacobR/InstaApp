package com.example.instaapp_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;
import com.example.instaapp_client.databinding.ListItemBinding;
import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.view.PostActivity;
import com.example.instaapp_client.viewmodel.PostViewModel;

import java.util.List;

public class UserPostAdapter extends BaseAdapter {

    private List<Post> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public UserPostAdapter(List<Post> list, Context context){

        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View root = convertView;

        ListItemBinding binding;

        if(root == null){
            if(layoutInflater == null){
                layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            binding = ListItemBinding.inflate(layoutInflater, viewGroup, false);
            root = binding.getRoot();
            root.setTag(binding);
        }else{
            binding = (ListItemBinding) root.getTag();
        }

        binding.seeMore.setOnClickListener( v -> {
            Intent intent = new Intent(context, PostActivity.class);
            intent.putExtra("post", list.get(i));
            context.startActivity(intent);

        });

        boolean isVideo = true;

        if(list.get(i).getUrl().charAt(list.get(i).getUrl().lastIndexOf(".") + 1) == 'j'){
            isVideo = false;
        }

        if(isVideo){
            binding.post.removeView(binding.imageView);
            if(binding.post.getChildCount() == 1) {
                ExoPlayer player = new ExoPlayer.Builder(context).build();
                PlayerView playerView = new PlayerView(context);
                playerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000));
                binding.post.addView(playerView);
                playerView.setPlayer(player);
                MediaItem mediaItem = MediaItem.fromUri("http://192.168.119.67:3000/api/getfile/" + list.get(i).getId());
                player.setMediaItem(mediaItem);
                player.prepare();
                player.play();
            }
        }

        String status =list.get(i).getHistory().get(list.get(i).getHistory().size()-1).getStatus();
        String toUrl = "";
        if(status != null && status != "original"){
            toUrl += "/" + status;
        }

        binding.setPost(list.get(i));
        Glide.with(binding.imageView.getContext()).load("http://192.168.119.67:3000/api/getfile/" + list.get(i).getId() + toUrl).into(binding.imageView);
        return root;
    }
}
