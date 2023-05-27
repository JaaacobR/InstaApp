package com.example.instaapp_client.model;

public class History {
    private int timestamp;
    private String status;
    private String url;


    public History(int timestamp, String status, String url){
        this.timestamp = timestamp;
        this.url = url;
        this.status = status;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }
}
