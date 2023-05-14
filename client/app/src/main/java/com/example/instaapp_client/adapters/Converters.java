package com.example.instaapp_client.adapters;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Converters {
    @BindingAdapter("bind:photoUrl")
    public static void changeUrl(ImageView imageView, String imageUrl){
        Log.d("image123" , imageUrl);
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);

    }
}
