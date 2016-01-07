package shared.model.user;

import shared.model.event.FollowerEvents;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
public class Following extends User implements Serializable { // those who followed by users
    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Follower> followers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FollowerEvents> follower_EventsList = new ArrayList<>();

    public Following() {
    }

    public Following(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Set<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Follower> followers) {
        this.followers = followers;
    }

    public void addFollower(Follower follower) {
        followers.add(follower);
    }

    public void addFollower_Events(FollowerEvents follower_events) {
        follower_EventsList.add(follower_events);
    }

    public List<FollowerEvents> getFollower_EventsList() {
        return follower_EventsList;
    }
}
