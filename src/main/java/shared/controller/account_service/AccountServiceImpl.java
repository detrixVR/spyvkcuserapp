package shared.controller.account_service;

import com.google.inject.Inject;
import shared.controller.db_service.IDBService;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements IAccountService {
    private Map<Long, Follower> users = new HashMap<>();
    private Map<String, Follower> sessions = new HashMap<>();
    private IDBService dbService;

    @Inject
    public AccountServiceImpl(IDBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public boolean addFollower(Long id, Follower user) {
        users.put(id, user);
        dbService.saveFollower(user);
        return true;
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
    public Follower getUser(Long id) {
        return users.get(id);
    }

    @Override
    public int getUsersCount() {
        return users.size();
    }

    @Override
    public void addFollowing(Follower follower, Following following) {
        follower.addFollowing(following);
        following.addFollower(follower);
        dbService.updateFollower(follower);
    }

}
