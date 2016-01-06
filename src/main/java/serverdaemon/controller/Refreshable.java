package serverdaemon.controller;

import shared.model.user.Follower;
import shared.model.user.Following;

public interface Refreshable<T> {
    T refresh(Following following, Follower follower);
}
