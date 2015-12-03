package shared.controller.account_service;

import shared.model.user.Follower;
import shared.model.user.Following;

public interface IAccountService {
    boolean addFollower(Long id, Follower user);

    void addSession(String sessionId, Follower user);

    Follower getSessions(String sessionId);

    Follower getUser(Long id);

    int getUsersCount();

    void addFollowing(Follower follower, Following following);
}
