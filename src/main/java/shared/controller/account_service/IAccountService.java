package shared.controller.account_service;

import shared.model.user.Follower;

public interface IAccountService {
    void saveFollower(Long id, Follower user);

    void addSession(String sessionId, Follower user);

    Follower getSessions(String sessionId);

    Follower getFollower(Long id);

    int getUsersCount();
}
