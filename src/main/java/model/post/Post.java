package model.post;

import java.util.ArrayList;

public class Post {
    private Long id = 0L;
    private String text = "";
    private ArrayList<Long> likedUserIds = new ArrayList<>();

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
       text = text;
    }

    @Override
    public boolean equals(Object obj) {
        Post post = (Post) obj;
        return this.id.equals(post.id) && this.text.equals(post.text) && this.likedUserIds.equals(post.likedUserIds);
    }
}
