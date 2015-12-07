package shared.model.dao;

import org.hibernate.Session;
import shared.model.post.PostSnapshot;

public class PostSnapshotDAO extends DAO<PostSnapshot> {

    public PostSnapshotDAO(Session session, Class<PostSnapshot> typeClass) {
        super(session, typeClass);
    }

}