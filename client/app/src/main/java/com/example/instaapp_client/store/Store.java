package com.example.instaapp_client.store;

public class Store {
    private static String token;
    private static String login;

    public static void setToken(String newToken){
        token = newToken;
    }

    public static String getToken(){
        return token;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Store.login = login;
    }
}
