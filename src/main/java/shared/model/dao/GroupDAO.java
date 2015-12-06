package shared.model.dao;

import org.hibernate.Session;
import shared.model.group.Group;

public class GroupDAO extends DAO<Group> {

    public GroupDAO(Session session) {
        super(session, Group.class);
    }
}
