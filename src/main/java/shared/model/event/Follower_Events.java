package shared.model.event;

import shared.model.user.Follower;

import java.util.ArrayList;
import java.util.List;

public class Follower_Events {
    private Follower follower;
    private List<Event> events = new ArrayList<>();

    public Follower_Events(Follower follower, List<Event> events) {
        this.follower = follower;
        this.events = events;
    }

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
