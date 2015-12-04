package shared.controller.account_service;

import shared.model.group.Group;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.List;
import java.util.Set;

public interface IAccountService {
    boolean addFollower(Long id, Follower user);

    void addSession(String sessionId, Follower user);

    Follower getSessions(String sessionId);

    Follower getFollower(Long id);

    Following getFollowing(Long id);

    int getUsersCount();

    void addFollowing(Follower follower, Following following);

    void addGroupsToFollowing(Following following, Set<Group> groups, List<Integer> count);
}
