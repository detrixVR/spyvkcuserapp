package shared.controller.db_service;

import shared.model.user.Follower;
import shared.model.user.Following;

public interface IDBService {
    void saveFollower(Follower follower);

    void saveFollowing(Following following);

    void updateFollower(Follower follower);
}