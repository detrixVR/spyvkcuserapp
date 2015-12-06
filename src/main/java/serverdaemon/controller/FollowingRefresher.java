package serverdaemon.controller;

import serverdaemon.controller.logic.IAppLogic;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.Following;

import java.util.HashSet;
import java.util.Set;

public class FollowingRefresher implements Refreshable<Following> {
    private final IAppLogic appLogic;
    private final IDBService dbService;

    public FollowingRefresher(IAppLogic appLogic, IDBService dbService) {
        this.appLogic = appLogic;
        this.dbService = dbService;
    }

    public void refresh(Set<Following> following) {
        for (Following followingOne : following) {
            Set<Group> groupsOfFollowing = followingOne.getGroups().keySet();
            Set<Group> groupsWithLikedPosts = appLogic.filterGroupsByFollowingLike(
                    groupsOfFollowing,
                    followingOne.getUserInfo().getVkId()
            );
            Set<Post> likedPosts = new HashSet<>();
            groupsWithLikedPosts.forEach(g -> likedPosts.addAll(g.getPosts()));
            followingOne.setLikedPosts(likedPosts);
            dbService.updateFollowing(followingOne);
        }
    }
}
