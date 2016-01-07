package shared.model.dao;

import org.hibernate.Session;
import shared.model.snapshots.FriendListSnapshot;

public class FriendListSnapshotDAO extends DAO<FriendListSnapshot> {
    public FriendListSnapshotDAO(Session session) {
        super(session, FriendListSnapshot.class);
    }
}
