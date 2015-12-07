package serverdaemon.controller;

public interface SnapshotBuilder<Snapshot, Building> {
    Snapshot build(Building building);
}
