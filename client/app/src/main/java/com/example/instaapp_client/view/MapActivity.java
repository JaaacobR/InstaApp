package com.example.instaapp_client.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instaapp_client.R;
import com.example.instaapp_client.databinding.ActivityMainBinding;
import com.example.instaapp_client.databinding.ActivityMapBinding;
import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.requests.LocationRequest;
import com.example.instaapp_client.service.RetrofitService;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityMapBinding binding;
    private GoogleMap map;

    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.ACCESS_FINE_LOCATION"
    };

    private List<Address> list;
    private Geocoder geocoder;
    private String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().hide();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        Places.initialize(getApplicationContext(), "AIzaSyAwu6FO-Vb-ITp39cSydpdr7e6yYjdHP5k");
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setActivityMode(AutocompleteActivityMode.FULLSCREEN);
        autocompleteFragment.getView().setBackgroundColor(0x0000ff);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i("xxx", "Place: " + place.getName() + ", " + place.getId());
                try {
                    geocode(place.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(@NonNull Status status) {
                Log.i("xxx", "error: " + status);
            }
        });

        geocoder = new Geocoder(MapActivity.this);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_fragment);

        mapFragment.getMapAsync(this);

        binding.confirmBtn.setOnClickListener( v -> {
            Call<Post> call = RetrofitService.getPostInterface().setLocation(new LocationRequest(Long.parseLong(id), location));


            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Log.d("fail", "fail" + call.toString() + " " + t.getMessage());
                    finish();
                }
            });
        });


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
        location = locationName;
        binding.confirmBtn.setEnabled(true);


        double latitude = list.get(0).getLatitude();
        double longitude = list.get(0).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng , 10);
        map.animateCamera(cameraUpdate);

    }

}