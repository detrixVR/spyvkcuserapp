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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        EventAction eventAction = this.eventAction;
        if(eventAction == EventAction.ADD) stringBuilder.append("Добавил ");
        if(eventAction == EventAction.REMOVE) stringBuilder.append("Удалил ");
        stringBuilder.append("аудиозапись ");
        stringBuilder.append(audio.getArtist());
        stringBuilder.append(" - ");
        stringBuilder.append(audio.getTitle());
        stringBuilder.append(".");
        return stringBuilder.toString();
    }
}
