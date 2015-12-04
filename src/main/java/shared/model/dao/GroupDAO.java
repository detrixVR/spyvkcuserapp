package shared.model.dao;

import org.hibernate.Session;
import shared.model.group.Group;

public class GroupDAO {
    private Session session;

    public GroupDAO(Session session) {
        this.session = session;
    }

    public void save(Group group) {
        session.beginTransaction();
        session.save(group);
        session.getTransaction().commit();
    }
}
