package shared.model.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import shared.model.user.Following;

public class FollowingDAO extends DAO<Following> {

    public FollowingDAO(Session session) {
        super(session, Following.class);
    }

    public Following getByVkId(Long id) {
        session.beginTransaction();
        Following following = (Following) session.createCriteria(Following.class, "following")
                .createAlias("following.userInfo", "userInfo")
                .add(Restrictions.eq("userInfo.vkId", id))
                .uniqueResult();
        session.getTransaction().commit();
        return following;
    }

}
