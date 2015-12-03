package shared.model.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "followers")
public class Follower extends User implements Serializable { // those who follow for users
    @Column(name = "accessToken")
    private String accessToken;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Following> following;

    public Follower() {}

    public Follower(UserInfo userInfo, String accessToken) {
        this.userInfo = userInfo;
        this.accessToken = accessToken;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<Following> following) {
        this.following = following;
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
