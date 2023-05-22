package com.example.instaapp_client.requests;

public class RegisterRequest {
    private String login;
    private String fullName;
    private String email;
    private String password;

    public RegisterRequest(String login, String fullName, String email, String password) {
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    
}
