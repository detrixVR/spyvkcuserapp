package model;

import java.util.ArrayList;
import java.util.Objects;

public class Group {
    private Long id = 0L;
    private String name = "";
    private String screenName = "";
    private ArrayList<Post> posts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object obj) {
        Group group = (Group) obj;
        return Objects.equals(this.id, group.id) && this.name.equals(group.name) && this.screenName.equals(group.screenName) &&
                this.posts.equals(group.posts);
    }
}
