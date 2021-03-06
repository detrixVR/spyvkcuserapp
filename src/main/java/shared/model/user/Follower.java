package shared.model.user;

import shared.model.event.FollowingEventTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Follower extends User implements Serializable { // those who follow for users
    private String accessToken = "";

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Following> following = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FollowingEventTypes> following_EventTypesList = new ArrayList<>();

    public Follower() {
    }

    public Follower(UserInfo userInfo, String accessToken) {
        this.userInfo = userInfo;
        this.accessToken = accessToken;
    }

    public Set<Following> getFollowing() {
        return following;
    }

    public void setFollowing(HashSet<Following> following) {
        this.following = following;
    }

    public Following getFollowingByVkId(Long id) {
        Following retFollowing = null;
        for (Following followingOne : following) {
            if (followingOne.getUserInfo().getVkId().longValue() == id.longValue()) {
                retFollowing = followingOne;
                break;
            }
        }
        return retFollowing;
    }

    public void addFollowing(Following following) {
        this.following.add(following);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<FollowingEventTypes> getFollowing_EventTypesList() {
        return following_EventTypesList;
    }

    public void setFollowing_EventTypesList(List<FollowingEventTypes> following_EventTypesList) {
        this.following_EventTypesList = following_EventTypesList;
    }

    public void addFollowing_EventTypes(FollowingEventTypes following_eventTypes) {
        following_EventTypesList.add(following_eventTypes);
    }
}
