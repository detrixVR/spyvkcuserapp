package shared.model.group;

import shared.model.post.Post;
import shared.model.user.Following;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
public class Group {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private GroupInfo groupInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Following> following;

    public Group(GroupInfo groupInfo, ArrayList<Post> posts) {
        this.groupInfo = groupInfo;
        this.posts = posts;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

//    @Override
//    public boolean equals(Object obj) {
//        Group group = (Group) obj;
//        return Objects.equals(this.id, group.id) && this.name.equals(group.name) && this.screenName.equals(group.screenName) &&
//                this.posts.equals(group.posts);
//    }
}
