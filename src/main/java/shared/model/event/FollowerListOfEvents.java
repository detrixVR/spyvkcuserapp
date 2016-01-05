package shared.model.event;

import shared.model.user.Follower;

public class FollowerListOfEvents {
    private Follower follower;
    private ListOfEvents listOfEvents = new ListOfEvents();

    public FollowerListOfEvents(Follower follower) {
        this.follower = follower;
    }

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public ListOfEvents getListOfEvents() {
        return listOfEvents;
    }

    public void setListOfEvents(ListOfEvents listOfEvents) {
        this.listOfEvents = listOfEvents;
    }
}
