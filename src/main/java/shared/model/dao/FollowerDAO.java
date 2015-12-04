package shared.model.dao;

import shared.model.user.Follower;
import org.hibernate.Session;

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
}
