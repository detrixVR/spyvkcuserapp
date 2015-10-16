package model;

import java.util.ArrayList;

public class Post {
    private Long id;
    private String text;
    private ArrayList<Long> likedUserIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<Long> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(ArrayList<Long> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
