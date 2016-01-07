package serverdaemon.controller;

import shared.model.event.Event;
import shared.model.snapshots.GroupListSnapshot;
import shared.model.snapshots.GroupSnapshot;
import shared.model.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupLikeSnapshotDifference implements SnapshotDifference {
    public List<Event> difference(Snapshot s1, Snapshot s2) {
        List<Event> events = new ArrayList<>();
        GroupListSnapshot one = (GroupListSnapshot) s1;
        GroupListSnapshot two = (GroupListSnapshot) s2;
        Set<GroupSnapshot> oneSet = one.getGroupSnapshots().stream().collect(Collectors.toSet());
        Set<GroupSnapshot> twoSet = two.getGroupSnapshots().stream().collect(Collectors.toSet());
        return null;
    }

    @Override
    public List<Event> difference(Snapshot s1, Snapshot s2, List<Event> events) {
        return null;
    }
}
