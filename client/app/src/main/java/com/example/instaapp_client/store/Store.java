package com.example.instaapp_client.store;

public class Store {
    private static String token;

    public static void setToken(String newToken){
        token = newToken;
    }

    public static String getToken(){
        return token;
    }
}
