package shared.model.event;

import shared.model.friend.Friend;

import javax.persistence.*;

@Entity
@Table
public class FriendEvent extends Event {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Friend friend;

    public FriendEvent(EventAction eventAction) {
        this.eventType = EventType.FRIEND;
        this.eventAction = eventAction;
    }

    public FriendEvent() {}

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        EventAction eventAction = this.eventAction;
        if(eventAction == EventAction.ADD) stringBuilder.append("Добавил ");
        if(eventAction == EventAction.REMOVE) stringBuilder.append("Удалил ");
        stringBuilder.append("друга под именем ");
        stringBuilder.append(friend.getFirstName());
        stringBuilder.append(" ");
        stringBuilder.append(friend.getLastName());
        stringBuilder.append(".");
        return stringBuilder.toString();
    }
}
