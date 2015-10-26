package controller.account_service;

import model.user.User;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements IAccountService {
    private Map<Long, User> users = new HashMap<>();
    private Map<String, User> sessions = new HashMap<>();

    @Override
    public boolean addUser(Long id, User user) {
        users.put(id, user);
        return true;
    }

    @Override
    public void addSession(String sessionId, User user) {
        sessions.put(sessionId, user);
    }

    @Override
    public User getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public User getUser(Long id) {
        return users.get(id);
    }

    @Override
    public int getUsersCount() {
        return users.size();
    }

}
