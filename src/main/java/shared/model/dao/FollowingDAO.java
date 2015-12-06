package shared.model.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import shared.model.user.Following;

import java.util.HashSet;
import java.util.Set;

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

    public Set<Following> getAll() {
        session.beginTransaction();
        HashSet following = new HashSet(session.createCriteria(Following.class).list());
        session.getTransaction().commit();
        return following;
    }
}
