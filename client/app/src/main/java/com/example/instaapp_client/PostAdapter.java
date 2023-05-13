package com.example.instaapp_client;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.instaapp_client.databinding.ListItemBinding;
import com.example.instaapp_client.viewmodel.PostViewModel;

public class PostAdapter extends BaseAdapter {

    private PostViewModel postViewModel;
    private LayoutInflater layoutInflater;

    public PostAdapter(PostViewModel postViewModel){
        this.postViewModel = postViewModel;
    }


    @Override
    public int getCount() {
        if(postViewModel.getObservedPosts().getValue() == null){
            return 0;
        }else{
            return postViewModel.getObservedPosts().getValue().size();
        }
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

        binding.setPost(postViewModel.getObservedPosts().getValue().get(i));
        return root;
    }
}
