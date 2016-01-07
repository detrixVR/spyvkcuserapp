package shared.model.event;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    protected EventType eventType;

    protected EventAction eventAction;

    protected Long eventDate;

    public enum EventAction {
        ADD,
        REMOVE
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public EventAction getEventAction() {
        return eventAction;
    }

    public void setEventAction(EventAction eventAction) {
        this.eventAction = eventAction;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public EventType getEventType() {
        return eventType;
    }
}
