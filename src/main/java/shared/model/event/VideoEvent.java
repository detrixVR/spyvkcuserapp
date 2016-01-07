package shared.model.event;

import shared.model.video.Video;

import javax.persistence.*;

@Entity
@Table
public class VideoEvent extends Event {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Video video;

    public VideoEvent(EventAction eventAction) {
        this.eventType = EventType.VIDEO;
        this.eventAction = eventAction;
    }

    public VideoEvent() {}

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        EventAction eventAction = this.eventAction;
        if(eventAction == EventAction.ADD) stringBuilder.append("Добавил ");
        if(eventAction == EventAction.REMOVE) stringBuilder.append("Удалил ");
        stringBuilder.append("видеозапись ");
        stringBuilder.append(video.getTitle());
        stringBuilder.append(".");
        return stringBuilder.toString();
    }
}
