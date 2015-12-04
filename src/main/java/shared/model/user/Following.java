package shared.model.user;

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
    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @CollectionTable(name = "following_group", joinColumns = @JoinColumn(name = "following_id"))
    @MapKeyJoinColumn(name = "group_id")
    @Column(name = "count")
    private Map<Group, Integer> groups = new HashMap<>();

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Follower> followers = new HashSet<>();

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

    public Map<Group, Integer> getGroups() {
        return groups;
    }

    public void setGroups(Map<Group, Integer> groups) {
        this.groups = groups;
    }

    public void addFollower(Follower follower) {
        followers.add(follower);
    }
}
