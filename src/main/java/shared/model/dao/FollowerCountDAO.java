package shared.model.dao;

import org.hibernate.Session;
import shared.model.user.FollowerCount;

public class FollowerCountDAO {
    private Session session;

    public FollowerCountDAO(Session session) {
        this.session = session;
    }

    public void save(FollowerCount followerCount) {
        session.beginTransaction();
        session.save(followerCount);
        session.getTransaction().commit();
    }

    public void update(FollowerCount followerCount) {
        session.beginTransaction();
        session.update(followerCount);
        session.getTransaction().commit();
    }
}
