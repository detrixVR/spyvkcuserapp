package shared.model.user;

import shared.model.group.Group;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "following")
public class Following extends User implements Serializable { // those who followed by users
    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Group> groups;

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follower> followers;

    public Following() {}

    public Following(List<Follower> followers) {
        this.followers = followers;
    }

    public List<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
