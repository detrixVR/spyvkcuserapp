package shared.model.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import shared.model.user.Follower;

public class FollowerDAO extends DAO<Follower> {

    public FollowerDAO(Session session) {
        super(session, Follower.class);
    }

    public Follower getByVkId(Long id) {
        session.beginTransaction();
        Follower follower = (Follower) session
                .createCriteria(Follower.class)
                .createAlias("userInfo", "userInfo")
                .add(Restrictions.eq("userInfo.vkId", id))
                .uniqueResult();
        session.getTransaction().commit();
        return follower;
    }
}
