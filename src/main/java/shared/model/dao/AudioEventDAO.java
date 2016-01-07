package shared.model.dao;

import org.hibernate.Session;
import shared.model.event.AudioEvent;

public class AudioEventDAO extends DAO<AudioEvent> {
    public AudioEventDAO(Session session) {
        super(session, AudioEvent.class);
    }
}
