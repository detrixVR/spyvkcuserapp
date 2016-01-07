package shared.model.dao;

import org.hibernate.Session;
import shared.model.snapshots.AudioListSnapshot;

public class AudioListSnapshotDAO extends DAO<AudioListSnapshot> {
    public AudioListSnapshotDAO(Session session) {
        super(session, AudioListSnapshot.class);
    }
}
