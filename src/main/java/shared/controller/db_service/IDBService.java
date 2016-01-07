package shared.controller.db_service;

import shared.model.event.*;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.Map;

public interface IDBService {
    void saveFollower(Follower follower);

    void saveFollowing(Following following);

    void updateFollower(Follower follower);

    <T> void save(T t, Class<T> classT);

    void updateFollowing(Following following);

    Map<Long, Follower> getAllFollowers();

    Follower getFollowerByVkId(Long id);

    void updateFollowerEvents(FollowerEvents followerEvents);
}