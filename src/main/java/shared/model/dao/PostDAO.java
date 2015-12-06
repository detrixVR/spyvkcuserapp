package shared.model.dao;

import org.hibernate.Session;
import shared.model.post.Post;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostDAO {
    private Session session;

    public PostDAO(Session session) {
        this.session = session;
    }

    public void save(Post post) {
        session.beginTransaction();
        session.save(post);
        session.getTransaction().commit();
    }

    public Set<Post> getAll() {
        List<Post> list = session.createCriteria(Post.class).list();
        return new HashSet<>(list);
    }

    public void update(Post post) {
        session.beginTransaction();
        session.update(post);
        session.getTransaction().commit();
    }
}
