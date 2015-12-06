package shared.model.group;

import shared.model.post.Post;
import shared.model.user.Following;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private GroupInfo groupInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Post> posts = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<Following> following = new HashSet<>();

    public Group(GroupInfo groupInfo, Set<Post> posts) {
        this.groupInfo = groupInfo;
        this.posts = posts;
    }

    public Group(GroupInfo info) {
        this.groupInfo = info;
    }

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Following> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Following> following) {
        this.following = following;
    }

    public void addFollowing(Following following) {
        this.following.add(following);
    }
}
