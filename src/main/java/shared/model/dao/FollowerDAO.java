package shared.model.dao;

import org.hibernate.Session;
import shared.model.user.Follower;

public class FollowerDAO extends DAO<Follower> {

    public FollowerDAO(Session session) {
        super(session, Follower.class);
    }

}
