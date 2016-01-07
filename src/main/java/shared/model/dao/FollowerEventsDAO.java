package shared.model.dao;

import org.hibernate.Session;
import shared.model.event.FollowerEvents;

public class FollowerEventsDAO extends DAO<FollowerEvents> {
    public FollowerEventsDAO(Session session) {
        super(session, FollowerEvents.class);
    }
}
