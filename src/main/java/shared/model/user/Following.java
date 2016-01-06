package shared.model.user;

import org.hibernate.annotations.Cascade;
import shared.model.event.Follower_Events;
import shared.model.group.Group;
import shared.model.post.Post;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "following")
public class Following extends User implements Serializable { // those who followed by users
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "following_group_followercount",
            joinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @MapKeyJoinColumn(name = "followercount_id")
    private Map<Group, FollowerCount> groups = new HashMap<>();

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Follower> followers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follower_Events> follower_EventsList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> likedPosts = new HashSet<>();

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

    public Map<Group, FollowerCount> getGroups() {
        return groups;
    }

    public void setGroups(Map<Group, FollowerCount> groups) {
        this.groups = groups;
    }

    public void addFollower(Follower follower) {
        followers.add(follower);
    }

    public void addGroup(Group group, FollowerCount followerCount) {
        groups.put(group, followerCount);
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public void addFollower_Events(Follower_Events follower_events) {
        follower_EventsList.add(follower_events);
    }

    public List<Follower_Events> getFollower_EventsList() {
        return follower_EventsList;
    }
}
