package shared.controller.db_service;

import shared.model.user.Follower;

public interface IDBService {
    void saveUser(Follower user);
}