package serverdaemon.controller;

import shared.model.event.Event;
import shared.model.snapshots.Snapshot;

import java.util.List;

public interface SnapshotDifference {
    List<Event> difference(Snapshot s1, Snapshot s2, List<Event> events);
}
