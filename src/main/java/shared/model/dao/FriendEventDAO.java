package shared.model.dao;

import org.hibernate.Session;
import shared.model.event.FriendEvent;

public class FriendEventDAO extends DAO<FriendEvent> {
    public FriendEventDAO(Session session) {
        super(session, FriendEvent.class);
    }
}
