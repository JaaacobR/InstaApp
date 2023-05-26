package com.example.instaapp_client.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        replaceFragment(new Home());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.Home:
                    replaceFragment(new Home());
                    break;
                case R.id.search:
                    replaceFragment(new Search());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }
}