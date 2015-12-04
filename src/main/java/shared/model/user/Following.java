package shared.model.user;

import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import shared.model.group.Group;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "following")
public class Following extends User implements Serializable { // those who followed by users
//    @ElementCollection
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    @CollectionTable(name = "following_group")
//    @MapKeyClass(FollowerCount.class)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "following_group_followercount",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "followercount_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @MapKeyJoinColumn(name = "group_id")
    private Map<Group, FollowerCount> groups = new HashMap<>();

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Follower> followers = new HashSet<>();

    public Following() { }

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
}
