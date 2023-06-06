package com.example.instaapp_client.requests;

public class FilterRequest {
    private long id;
    private String url;
    private String type;
    private String params;

    public FilterRequest(long id, String url, String type, String params) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.params = params;
    }
}
