package com.example.instaapp_client.model;

import java.util.List;

public class Post {
    private Long id;

    private String album;

    private String originalName;

    private String url;

    private String lastChange;

    private List<String> tags;

    private List<History> history;


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", Album='" + album + '\'' +
                ", originalName='" + originalName + '\'' +
                ", url='" + url + '\'' +
                ", lastChange='" + lastChange + '\'' +
                ", tags=" + tags +
                ", history=" + history +
                '}';
    }
}
