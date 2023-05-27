package com.example.instaapp_client.view;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.FragmentCameraBinding;
import com.example.instaapp_client.databinding.FragmentHomeBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.security.Timestamp;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class Camera extends Fragment {

    private FragmentCameraBinding binding;

    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    private int PERMISSIONS_REQUEST_CODE = 100;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private ImageCapture imageCapture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        if (!checkIfPermissionsGranted()) {
            requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        } else {
            cameraStart();
        }

        binding.takePhotoBtn.setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, new Date().getTime());
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(
                            Camera.this.getContext().getContentResolver(),
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues)
                            .build();
            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(Camera.this.getContext()),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            // photo saved!!!
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            // error
                        }
                    });
        });
        return view;
    }


    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(Camera.this.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraStart();
                } else {
                    //nie
                }
                break;

        }

    }

    public void cameraStart() {
        cameraProviderFuture = ProcessCameraProvider.getInstance((Camera.this.getContext()));
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (InterruptedException | ExecutionException e) {
                // No errors need to be handled for this Future. This should never be reached.
            }
        }, ContextCompat.getMainExecutor(Camera.this.getContext()));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(binding.camera.getDisplay().getRotation())
                        .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.camera.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);
    }


}