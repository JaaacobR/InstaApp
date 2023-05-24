package com.example.instaapp_client.model;

public class User {
    private String login;
    private String fullName;
    private String email;
    private boolean confirmed;
    private String token;

    public String getToken() {
        return token;
    }
}
