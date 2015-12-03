package serverdaemon.controller.account_service;

import shared.model.user.Follower;

public interface IAccountService {
    boolean addUser(Long id, Follower user);

    void addSession(String sessionId, Follower user);

    Follower getSessions(String sessionId);

    Follower getUser(Long id);

    int getUsersCount();
}
