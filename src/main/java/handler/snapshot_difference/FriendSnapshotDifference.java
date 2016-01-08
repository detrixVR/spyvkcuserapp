package handler.snapshot_difference;

import shared.model.event.Event;
import shared.model.event.FriendEvent;
import shared.model.friend.Friend;
import shared.model.snapshots.FriendListSnapshot;

import java.util.List;

public class FriendSnapshotDifference extends SnapshotDifference<FriendListSnapshot, Friend, FriendEvent> {
    @Override
    protected void setAction(FriendEvent concreteEvent, Friend action) {
        concreteEvent.setFriend(action);
    }

    @Override
    protected Friend getConcreteEventEntity(FriendEvent concreteEvent) {
        return concreteEvent.getFriend();
    }

    @Override
    protected List<Friend> getListOfEventEntity(FriendListSnapshot friendListSnapshot) {
        return friendListSnapshot.getFriendList();
    }

    @Override
    protected FriendEvent createTypeOfEvent(Event.EventAction eventAction) {
        return new FriendEvent(eventAction);
    }
}
