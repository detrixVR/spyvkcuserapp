package shared.model.dao;

import org.hibernate.Session;
import shared.model.event.VideoEvent;

public class VideoEventDAO extends DAO<VideoEvent> {
    public VideoEventDAO(Session session) {
        super(session, VideoEvent.class);
    }
}
