package shared.model.dao;

import org.hibernate.Session;
import shared.model.snapshots.GroupSnapshot;

public class GroupSnapshotDAO extends DAO<GroupSnapshot> {
    public GroupSnapshotDAO(Session session) {
        super(session, GroupSnapshot.class);
    }
}
