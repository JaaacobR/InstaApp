package com.example.instaapp_client.view;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp_client.PostAdapter;
import com.example.instaapp_client.R;
import com.example.instaapp_client.adapters.UserPostAdapter;
import com.example.instaapp_client.api.UserInterface;
import com.example.instaapp_client.databinding.FragmentHomeBinding;
import com.example.instaapp_client.databinding.FragmentProfileBinding;
import com.example.instaapp_client.requests.ProfileRequest;
import com.example.instaapp_client.store.Store;
import com.example.instaapp_client.viewmodel.PostViewModel;
import com.example.instaapp_client.viewmodel.ProfileViewModel;
import com.example.instaapp_client.viewmodel.UserViewModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    private ProfileViewModel profileViewModel;

    private UserPostAdapter adapter;


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
           if(data != null){
               Uri imgData = data.getData();
               String fileName =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";

               for(int i = imgData.toString().lastIndexOf('%') + 3; i < imgData.toString().length(); i++){
                   fileName += imgData.toString().charAt(i);
               }

               File file = new File(fileName);
               RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
               MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
               RequestBody album = RequestBody.create(MultipartBody.FORM, Store.getLogin());
               profileViewModel.sendPost(album, body);

           }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getUser();

        binding.logout.setOnClickListener(v -> {
            getActivity().finish();
        });



        profileViewModel.getObservedUser().observe(getViewLifecycleOwner(), l -> {
            if(profileViewModel.getObservedUser().getValue().getEmail() != null){
                binding.fullName.setText(profileViewModel.getObservedUser().getValue().getFullName());
                binding.login.setText(profileViewModel.getObservedUser().getValue().getLogin());
                binding.email.setText(profileViewModel.getObservedUser().getValue().getEmail());
                adapter = new UserPostAdapter(profileViewModel.getObservedUser().getValue().getPhotos(), getContext());
                binding.gridView.setAdapter(adapter);


                if(profileViewModel.getObservedUser().getValue().getProfile() != null){
                    Log.d("cacheTest", "test");
                    Glide.with(binding.imageView).load("http://192.168.119.67:3000/api/getfile/" + profileViewModel.getObservedUser().getValue().getLogin() + "/profile").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.imageView);
                }
            }
        });

        binding.addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 100); // 100 - stała wartość, która później posłuży do identyfikacji tej akcji
        });

        binding.editBtn.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(1000, 1000);
            dialog.setContentView(R.layout.dialog_edit_profile);

            EditText fullNameEdit = dialog.findViewById(R.id.editFullName);
            fullNameEdit.setText(profileViewModel.getObservedUser().getValue().getFullName());

            EditText emailEdit = dialog.findViewById(R.id.editEmail);
            emailEdit.setText(profileViewModel.getObservedUser().getValue().getEmail());

            dialog.findViewById(R.id.cancelBtn).setOnClickListener(s -> {
                dialog.dismiss();
            });

            dialog.findViewById(R.id.okBtn).setOnClickListener(s -> {
                profileViewModel.updateProfile(new ProfileRequest(fullNameEdit.getText().toString() , emailEdit.getText().toString()));
                dialog.dismiss();
            });


            dialog.show();
        });

        return view;


    }
}