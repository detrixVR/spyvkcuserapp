package shared.model.event;

import shared.model.user.Follower;

import java.util.ArrayList;
import java.util.List;

public class Follower_EventTypes {
    private Follower follower;
    private List<EventType> eventTypes = new ArrayList<>();

    public Follower_EventTypes(Follower follower, List<EventType> eventTypes) {
        this.follower = follower;
        this.eventTypes = eventTypes;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }
}
