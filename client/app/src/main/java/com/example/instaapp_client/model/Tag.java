package com.example.instaapp_client.model;

import java.io.Serializable;

public class Tag implements Serializable {
    private int id;
    private String tag;
    private int popularity;

    public Tag(int id, String tag, int popularity) {
        this.id = id;
        this.tag = tag;
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public int getPopularity() {
        return popularity;
    }
}
