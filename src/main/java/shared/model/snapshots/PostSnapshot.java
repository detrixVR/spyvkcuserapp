package shared.model.snapshots;

import shared.model.post.Post;

import javax.persistence.*;
import java.util.List;

@Entity
@Table

public class PostSnapshot extends Snapshot<Post> {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private GroupSnapshot groupSnapshot;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Post post;

    public GroupSnapshot getGroupSnapshot() {
        return groupSnapshot;
    }

    public void setGroupSnapshot(GroupSnapshot groupSnapshot) {
        this.groupSnapshot = groupSnapshot;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public List<Post> getList() {
        return null;
    }
}
