package com.example.instaapp_client.requests;

public class LoginRequest {
    private String login;
    private String password;

    public LoginRequest(String login, String fullName, String email, String password) {
        this.login = login;
        this.password = password;
    }
}
