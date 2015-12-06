package shared.controller.account_service;

import com.google.inject.Inject;
import shared.controller.db_service.IDBService;
import shared.model.user.Follower;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements IAccountService {
    private Map<Long, Follower> followers = new HashMap<>();
    private Map<String, Follower> sessions = new HashMap<>();

    @Inject
    public AccountServiceImpl(IDBService dbService) {
        dbService.getAllFollowers();
    }

    @Override
    public void saveFollower(Long id, Follower user) {
        followers.put(id, user);
    }

    @Override
    public void addSession(String sessionId, Follower user) {
        sessions.put(sessionId, user);
    }

    @Override
    public Follower getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public Follower getFollower(Long id) {
        return followers.get(id);
    }

    @Override
    public int getUsersCount() {
        return followers.size();
    }

}
