package shared.model.snapshots;
import shared.model.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class PostListSnapshot extends Snapshot<Post> {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Post> postList = new ArrayList<>();

    @Override
    public List<Post> getList() {
        return postList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
