package com.example.instaapp_client.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.ActivityMapBinding;
import com.example.instaapp_client.databinding.ActivityMapViewBinding;
import com.example.instaapp_client.databinding.ActivityPostBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    @NonNull ActivityMapViewBinding binding;
    private GoogleMap map;

    private List<Address> list;
    private Geocoder geocoder;

    private String location;

    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.ACCESS_FINE_LOCATION"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        geocoder = new Geocoder(MapViewActivity.this);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_fragment);

        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        location = bundle.getString("location");




    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (!checkIfPermissionsGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(REQUIRED_PERMISSIONS, 100);
            }
        } else {
            map = googleMap;


            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            try {
                geocode(location);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
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

                } else {
                    //nie
                }
                break;

        }

    }
    private void geocode(String locationName) throws IOException {

        list = geocoder.getFromLocationName(locationName, 1);
        double latitude = list.get(0).getLatitude();
        double longitude = list.get(0).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng , 10);
        map.animateCamera(cameraUpdate);

    }

}