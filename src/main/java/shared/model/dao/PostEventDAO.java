package shared.model.dao;

import org.hibernate.Session;
import shared.model.event.PostEvent;

public class PostEventDAO extends DAO<PostEvent> {
    public PostEventDAO(Session session) {
        super(session, PostEvent.class);
    }
}
