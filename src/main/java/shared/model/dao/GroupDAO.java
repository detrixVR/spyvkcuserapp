package shared.model.dao;

import org.hibernate.Session;
import shared.model.group.Group;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<Group> getAll() {
        List<Group> list = session.createCriteria(Group.class).list();
        return new HashSet<>(list);
    }
}
