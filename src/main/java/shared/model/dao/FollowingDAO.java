package shared.model.dao;

import org.hibernate.Session;
import shared.model.user.Follower;
import shared.model.user.Following;

public class FollowingDAO {
    private Session session;

    public FollowingDAO(Session session) {
        this.session = session;
    }

    public void save(Following user) {
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public Following read(long id) {
        return session.load(Following.class, id);
    }
}
