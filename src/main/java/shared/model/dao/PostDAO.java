package shared.model.dao;

import org.hibernate.Session;
import shared.model.post.Post;

public class PostDAO extends DAO<Post> {

    public PostDAO(Session session) {
        super(session, Post.class);
    }

}
