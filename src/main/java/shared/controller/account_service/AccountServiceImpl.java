package shared.controller.account_service;

import com.google.inject.Inject;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.Follower;
import shared.model.user.FollowerCount;
import shared.model.user.Following;

import java.util.*;

public class AccountServiceImpl implements IAccountService {
    private Map<Long, Follower> followers = new HashMap<>();
    private Map<String, Follower> sessions = new HashMap<>();
    private IDBService dbService;

    @Inject
    public AccountServiceImpl(IDBService dbService) {
        this.dbService = dbService;
        followers = dbService.getAllFollowers();
    }

    @Override
    public boolean addFollower(Long id, Follower user) {
        followers.put(id, user);
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
        return followers.get(id);
    }

    @Override
    public Following getFollowing(Long id) {
        return dbService.getFollowingByVkId(id);
    }

    @Override
    public int getUsersCount() {
        return followers.size();
    }

    @Override
    public void addFollowing(Follower follower, Following following) {
        follower.addFollowing(following);
        following.addFollower(follower);
        dbService.saveFollowing(following);
        dbService.updateFollower(follower);
    }

    @Override
    public void addGroupsToFollowing(Following following, Set<Group> groups, Follower follower, List<Integer> counts) {
        Iterator<Group> groupIterator= groups.iterator();
        Iterator<Integer> countIterator = counts.iterator();
        while(groupIterator.hasNext() || countIterator.hasNext()) {
            Group group = groupIterator.next();
            Integer count = countIterator.next();

            FollowerCount followerCount = new FollowerCount(follower, count);
            dbService.saveFollowerCount(followerCount);

            group.addFollowing(following);
            dbService.saveGroup(group);

            following.addGroup(group, followerCount);
            dbService.updateFollowing(following);
        }
    }

    @Override
    public Set<Group> getAllGroups() {
        return dbService.getAllGroups();
    }

    @Override
    public void updateGroup(Group group) {
        dbService.updateGroup(group);
    }

    @Override
    public Set<Post> getAllPosts() {
        return dbService.getAllPosts();
    }

    @Override
    public Set<Following> getAllFollowing() {
        return dbService.getAllFollowings();
    }

    @Override
    public void updateFollowing(Following following) {
        dbService.updateFollowing(following);
    }

}
