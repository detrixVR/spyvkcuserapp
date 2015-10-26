package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User {
    private UserInfo userInfo;
    private String accessToken;
    private ArrayList<Long> followedIds;

    public ArrayList<Long> getFollowerIds() {
        return followedIds;
    }

    public void setFollowedIds(ArrayList<Long> followedIds) {
        this.followedIds = followedIds;
    }

    public User(UserInfo userInfo, String accessToken) {
        this.userInfo = userInfo;
        this.accessToken = accessToken;
        followedIds = new ArrayList<>();
    }

    public void addFollowerId(Long id) {
        followedIds.add(id);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
