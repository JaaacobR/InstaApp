package com.example.instaapp_client.responses;

import com.example.instaapp_client.model.Post;
import com.example.instaapp_client.model.User;

import java.util.List;

public class AuthResponse {
    private String fullName;
    private String email;
    private String login;
    private String profile;
    private List<Post> photos;


    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public List<Post> getPhotos() {
        return photos;
    }

    public String getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", profile='" + profile + '\'' +
                ", photos=" + photos +
                '}';
    }
}
