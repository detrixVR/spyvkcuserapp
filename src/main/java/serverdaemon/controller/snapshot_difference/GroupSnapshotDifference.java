package serverdaemon.controller.snapshot_difference;

import shared.model.event.Event;
import shared.model.event.GroupEvent;
import shared.model.group.Group;
import shared.model.snapshots.GroupListSnapshot;

import java.util.List;

public class GroupSnapshotDifference extends SnapshotDifference<GroupListSnapshot, Group, GroupEvent> {
    @Override
    protected void setAction(GroupEvent concreteEvent, Group action) {
        concreteEvent.setGroup(action);
    }

    @Override
    protected Group getConcreteEventEntity(GroupEvent concreteEvent) {
        return concreteEvent.getGroup();
    }

    @Override
    protected List<Group> getListOfEventEntity(GroupListSnapshot groupListSnapshot) {
        return groupListSnapshot.getGroupList();
    }

    @Override
    protected GroupEvent createTypeOfEvent(Event.EventAction eventAction) {
        return new GroupEvent(eventAction);
    }
}
