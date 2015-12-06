package shared.model.dao;

import org.hibernate.Session;
import shared.model.user.FollowerCount;

public class FollowerCountDAO extends DAO<FollowerCount> {

    public FollowerCountDAO(Session session) {
        super(session, FollowerCount.class);
    }

}
