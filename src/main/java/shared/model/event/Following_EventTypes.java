package shared.model.event;

import shared.model.user.Following;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "following_eventtypes")
public class Following_EventTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Following following;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = EventType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Following_EventTypes_EventTypes")
    @Column(name = "eventtype")
    private List<EventType> eventTypes = new ArrayList<>();

    public Following_EventTypes() {}

    public Following_EventTypes(Following following, List<EventType> eventTypes) {
        this.following = following;
        this.eventTypes = eventTypes;
    }

    public void setFollowing(Following following) {
        this.following = following;
    }

    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }
}
