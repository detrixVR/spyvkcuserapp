package shared.model.group;

import shared.model.post.Post;

import javax.persistence.*;
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

//    @ElementCollection
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    @CollectionTable(name = "following_group")
//    @MapKeyJoinColumn(name = "following_id")
//    @MapKeyClass(FollowerCount.class)
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "following_group_followercount",
//            joinColumns = @JoinColumn(name = "group_id", table = "groups"),
//            inverseJoinColumns = @JoinColumn(name = "following_id", table = "following"))
//    @MapKeyJoinColumn(name = "followercount_id", table = "follower_count")
//    private Map<Following, FollowerCount> following = new HashMap<>();

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

//    public Map<Following, FollowerCount> getFollowing() {
//        return following;
//    }

//    public void setFollowing(Map<Following, FollowerCount> following) {
//        this.following = following;
//    }

//    public void addFollowing(Following following, FollowerCount followerCount) {
//        this.following.put(following, followerCount);
//    }
//    @Override
//    public boolean equals(Object obj) {
//        Group group = (Group) obj;
//        return Objects.equals(this.id, group.id) && this.name.equals(group.name) && this.screenName.equals(group.screenName) &&
//                this.posts.equals(group.posts);
//    }
}
