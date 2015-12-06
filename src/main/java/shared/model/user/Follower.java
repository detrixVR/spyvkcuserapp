package shared.model.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "followers")
public class Follower extends User implements Serializable { // those who follow for users
    @Column(name = "accessToken")
    private String accessToken = "";

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Following> following = new HashSet<>();

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
}
