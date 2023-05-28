package com.example.instaapp_client.model;

public class History {
    private long timestamp;
    private String status;
    private String url;


    public History(int timestamp, String status, String url){
        this.timestamp = timestamp;
        this.url = url;
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }
}
