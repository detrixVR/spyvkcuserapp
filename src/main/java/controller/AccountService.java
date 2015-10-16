package controller;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<Long, User> users = new HashMap<>();
    private Map<String, User> sessions = new HashMap<>();

    public boolean addUser(Long id, User user) {
        users.put(id, user);
        return true;
    }

    public void addSession(String sessionId, User user) {
        sessions.put(sessionId, user);
    }

    public User getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    public User getUser(Long id) {
        return users.get(id);
    }

    public int getUsersCount() {
        return users.size();
    }

}
