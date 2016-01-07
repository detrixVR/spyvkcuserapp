package shared.model.event;

import shared.model.user.Following;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class FollowingEventTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Following following;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = EventType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable
    @Column
    private List<EventType> eventTypes = new ArrayList<>();

    public FollowingEventTypes() {}

    public FollowingEventTypes(Following following, List<EventType> eventTypes) {
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

    public Following getFollowing() {
        return following;
    }
}
