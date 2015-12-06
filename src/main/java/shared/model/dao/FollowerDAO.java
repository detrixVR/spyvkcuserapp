package shared.model.dao;

import org.hibernate.Session;
import shared.model.user.Follower;

import java.util.HashSet;
import java.util.Set;

public class FollowerDAO {
    private Session session;

    public FollowerDAO(Session session) {
        this.session = session;
    }

    public void save(Follower user) {
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public Follower read(long id) {
        return session.load(Follower.class, id);
    }

    public void update(Follower follower) {
        session.beginTransaction();
        session.update(follower);
        session.getTransaction().commit();
    }

    public Set<Follower> getAll() {
        session.beginTransaction();
        Set<Follower> followers = new HashSet<>(session.createCriteria(Follower.class).list());
        return followers;
    }
}
