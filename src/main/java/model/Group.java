package model;

import java.util.ArrayList;

public class Group {
    private GroupInfo groupInfo;
    private ArrayList<Post> posts = new ArrayList<>();

    public Group(GroupInfo groupInfo, ArrayList<Post> posts) {
        this.groupInfo = groupInfo;
        this.posts = posts;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

//    @Override
//    public boolean equals(Object obj) {
//        Group group = (Group) obj;
//        return Objects.equals(this.id, group.id) && this.name.equals(group.name) && this.screenName.equals(group.screenName) &&
//                this.posts.equals(group.posts);
//    }
}
