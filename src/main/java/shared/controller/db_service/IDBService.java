package shared.controller.db_service;

import shared.model.group.Group;
import shared.model.user.Follower;
import shared.model.user.Following;

public interface IDBService {
    void saveFollower(Follower follower);

    void saveFollowing(Following following);

    void updateFollower(Follower follower);

    void updateFollowing(Following following);

    Following getFollowingByVkId(Long id);

    void saveGroup(Group group);
}