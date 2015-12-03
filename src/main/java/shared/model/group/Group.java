package shared.model.group;

import shared.model.post.Post;
import shared.model.user.Following;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private GroupInfo groupInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Post> posts = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "following_group", joinColumns = @JoinColumn(name = "group_id"))
    @MapKeyJoinColumn(name = "following_id")
    @Column(name = "count")
    private Map<Following, Integer> following = new HashMap<>();

    public Group(GroupInfo groupInfo, Set<Post> posts) {
        this.groupInfo = groupInfo;
        this.posts = posts;
    }

    public Group(GroupInfo info) {
        this.groupInfo = info;
    }

    public Group() {}

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

    public Map<Following, Integer> getFollowing() {
        return following;
    }

    public void setFollowing(Map<Following, Integer> following) {
        this.following = following;
    }

//    @Override
//    public boolean equals(Object obj) {
//        Group group = (Group) obj;
//        return Objects.equals(this.id, group.id) && this.name.equals(group.name) && this.screenName.equals(group.screenName) &&
//                this.posts.equals(group.posts);
//    }
}
