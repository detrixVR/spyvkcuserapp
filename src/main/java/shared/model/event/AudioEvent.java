package shared.model.event;

import shared.model.audio.Audio;

import javax.persistence.*;

@Entity
@Table
public class AudioEvent extends Event {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Audio audio;

    public AudioEvent(EventAction eventAction) {
        this.eventType = EventType.AUDIO;
        this.eventAction = eventAction;
    }

    public AudioEvent() {}

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
