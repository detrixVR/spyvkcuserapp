package shared.model.dao;

import org.hibernate.Session;
import shared.model.snapshots.VideoListSnapshot;

public class VideoListSnapshotDAO extends DAO<VideoListSnapshot> {

    public VideoListSnapshotDAO(Session session) {
        super(session, VideoListSnapshot.class);
    }
}
