package shared.controller.account_service;

import shared.model.user.Follower;

public interface IAccountService {
    void saveFollower(Long id, Follower user);

    Follower getFollower(Long id);

    int getUsersCount();
}
