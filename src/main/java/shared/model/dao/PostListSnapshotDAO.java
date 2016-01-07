package shared.model.dao;

import org.hibernate.Session;
import shared.model.snapshots.PostListSnapshot;

public class PostListSnapshotDAO extends DAO<PostListSnapshot>{
    public PostListSnapshotDAO(Session session) {
        super(session, PostListSnapshot.class);
    }
}
