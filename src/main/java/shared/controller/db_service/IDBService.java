package shared.controller.db_service;

import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.Follower;
import shared.model.user.FollowerCount;
import shared.model.user.Following;

import java.util.Map;
import java.util.Set;

public interface IDBService {
    void saveFollower(Follower follower);

    void saveFollowing(Following following);

    void updateFollower(Follower follower);

    void updateFollowing(Following following);

    Following getFollowingByVkId(Long id);

    void saveGroup(Group group);

    Set<Group> getAllGroups();

    Map<Long, Follower> getAllFollowers();

    void saveFollowerCount(FollowerCount followerCount);

    void updateGroup(Group group);

    Set<Post> getAllPosts();

    Set<Following> getAllFollowings();
}