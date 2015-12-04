package shared.model.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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
    }

    public Following getById(long id) {
        return session.load(Following.class, id);
    }

    public void update(Following following) {
        session.beginTransaction();
        session.update(following);
        session.getTransaction().commit();
    }

    public Following getByVkId(Long id) {
        session.beginTransaction();
        return (Following) session.createCriteria(Following.class, "following")
                .createAlias("following.userInfo", "userInfo")
                .add(Restrictions.eq("userInfo.vkId", id))
                .uniqueResult();
    }
}
