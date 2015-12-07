package shared.model.dao;

import org.hibernate.Session;
import shared.model.group.GroupSnapshot;

public class GroupSnapshotDAO extends DAO<GroupSnapshot> {

    public GroupSnapshotDAO(Session session, Class<GroupSnapshot> typeClass) {
        super(session, typeClass);
    }

}
