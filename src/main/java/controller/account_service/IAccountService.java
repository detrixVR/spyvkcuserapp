package controller.account_service;

import model.user.User;

/**
 * Created by aminought on 27.10.2015.
 */
public interface IAccountService {
    boolean addUser(Long id, User user);

    void addSession(String sessionId, User user);

    User getSessions(String sessionId);

    User getUser(Long id);

    int getUsersCount();
}
