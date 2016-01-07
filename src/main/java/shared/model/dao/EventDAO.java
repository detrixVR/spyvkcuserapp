package shared.model.dao;

import org.hibernate.Session;
import shared.model.event.Event;

public class EventDAO extends DAO<Event> {
    public EventDAO(Session session) {
        super(session, Event.class);
    }
}
