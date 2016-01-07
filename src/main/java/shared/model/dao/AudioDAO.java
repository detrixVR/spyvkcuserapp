package shared.model.dao;

import org.hibernate.Session;
import shared.model.audio.Audio;

public class AudioDAO extends DAO<Audio> {
    public AudioDAO(Session session) {
        super(session, Audio.class);
    }
}
