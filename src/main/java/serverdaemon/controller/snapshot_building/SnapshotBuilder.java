package serverdaemon.controller.snapshot_building;

import shared.model.user.Follower;
import shared.model.user.Following;

public interface SnapshotBuilder<T> {
    T build(Following following, Follower follower);
}
