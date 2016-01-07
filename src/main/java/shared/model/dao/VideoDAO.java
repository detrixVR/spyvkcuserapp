package shared.model.dao;

import org.hibernate.Session;
import shared.model.video.Video;

public class VideoDAO extends DAO<Video> {
    public VideoDAO(Session session) {
        super(session, Video.class);
    }
}
