package shared.model.event;

import shared.model.user.Following;

import java.util.ArrayList;
import java.util.List;

public class Following_EventTypes {
    private Following following;
    private List<EventType> eventTypes = new ArrayList<>();

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
