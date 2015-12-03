package shared.model.dao;

import shared.model.user.Follower;
import org.hibernate.Session;

public class UserDAO {
    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public void save(Follower user) {
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public Follower read(long id) {
        return session.load(Follower.class, id);
    }
}
