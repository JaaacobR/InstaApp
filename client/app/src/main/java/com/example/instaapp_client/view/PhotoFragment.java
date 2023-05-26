package com.example.instaapp_client.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.FragmentHomeBinding;
import com.example.instaapp_client.databinding.FragmentPhotoBinding;


public class PhotoFragment extends Fragment {

    FragmentPhotoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }
}