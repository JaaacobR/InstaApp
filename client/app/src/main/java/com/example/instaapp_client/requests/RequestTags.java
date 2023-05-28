package com.example.instaapp_client.requests;

import com.example.instaapp_client.model.Tag;

import java.util.List;

public class RequestTags {
    private long id;
    private List<Tag> tags;

    public RequestTags(long id, List<Tag> tags) {
        this.id = id;
        this.tags = tags;
    }
}
