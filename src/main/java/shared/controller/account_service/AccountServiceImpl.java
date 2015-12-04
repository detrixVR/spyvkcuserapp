package shared.controller.account_service;

import com.google.inject.Inject;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.*;

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
    public Follower getFollower(Long id) {
        return users.get(id);
    }

    @Override
    public Following getFollowing(Long id) {
        return dbService.getFollowingByVkId(id);
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

    @Override
    public void addGroupsToFollowing(Following following, Set<Group> groups, List<Integer> counts) {
        Map<Group, Integer> groupCountMap = new HashMap<>();
        Iterator<Group> groupIterator= groups.iterator();
        Iterator<Integer> countIterator = counts.iterator();
        while(groupIterator.hasNext() || countIterator.hasNext()) {
            Group group= groupIterator.next();
            Integer count = countIterator.next();
            group.addFollowing(following, count);
            groupCountMap.put(group, count);
            dbService.saveGroup(group);
        }
        following.setGroups(groupCountMap);

        dbService.updateFollowing(following);
    }

}
