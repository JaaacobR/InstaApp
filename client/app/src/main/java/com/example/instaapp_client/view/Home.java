package com.example.instaapp_client.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp_client.PostAdapter;
import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.FragmentHomeBinding;
import com.example.instaapp_client.viewmodel.PostViewModel;


public class Home extends Fragment {

    private FragmentHomeBinding binding;

    private PostViewModel postViewModel;

    private PostAdapter adapter;




    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        postViewModel.getPosts();

        binding.setPostViewModel(postViewModel);

        adapter = new PostAdapter(postViewModel);

        postViewModel.getObservedPosts().observe(this, l -> {
            adapter = new PostAdapter(postViewModel);
            binding.gridView.setAdapter(adapter);
        });


        return view;
    }
}