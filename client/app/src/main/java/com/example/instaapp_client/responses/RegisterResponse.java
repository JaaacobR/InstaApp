package com.example.instaapp_client.responses;

public class RegisterResponse {
    private String url;

    public String getUrl() {
        return "http://192.168.119.67:3000/api/user/confirm/" + url;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "url='" + url + '\'' +
                '}';
    }
}
