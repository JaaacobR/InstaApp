package com.example.instaapp_client.requests;

public class LocationRequest {
    private long id;
    private String location;

    public LocationRequest(long id, String location) {
        this.id = id;
        this.location = location;
    }
}
