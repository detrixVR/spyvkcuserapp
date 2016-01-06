package shared.model.event;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    public EventType eventType;

    public EventAction eventAction;

    public Long eventDate;

    public enum EventAction {
        ADD,
        REMOVE
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public EventType getEventType() {
        return eventType;
    }
}
