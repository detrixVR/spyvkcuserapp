package serverdaemon.controller;

import shared.model.event.Event;
import shared.model.snapshots.GroupListSnapshot;
import shared.model.snapshots.GroupSnapshot;
import shared.model.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupLikeSnapshotDifference extends SnapshotDifference {

    @Override
    protected void setAction(Event concreteEvent, Object action) {

    }

    @Override
    protected Object getConcreteEventEntity(Event concreteEvent) {
        return null;
    }

    @Override
    protected List getListOfEventEntity(Snapshot snapshot) {
        return null;
    }

    @Override
    protected Event createTypeOfEvent(Event.EventAction eventAction) {
        return null;
    }
}
