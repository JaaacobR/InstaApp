package com.example.instaapp_client.responses;

public class RegisterResponse {
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "url='" + url + '\'' +
                '}';
    }
}
