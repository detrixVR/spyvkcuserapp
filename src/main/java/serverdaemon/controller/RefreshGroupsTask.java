package serverdaemon.controller;

import com.google.inject.Inject;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.IApiService;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.Follower;
import shared.model.user.FollowerCount;
import shared.model.user.Following;

import java.util.*;

public class RefreshGroupsTask extends TimerTask {
    private IAccountService accountService;
    private IApiService apiService;
    private IAppLogic appLogic;

    @Inject
    private RefreshGroupsTask(IAccountService accountService, IApiService apiService, IAppLogic appLogic) {
        this.accountService = accountService;
        this.apiService = apiService;
        this.appLogic = appLogic;
    }

    @Override
    public void run() {
        Set<Group> groups = accountService.getAllGroups();
        for (Group group : groups) {
            Set<Following> following = group.getFollowing();
            Map<Integer, Follower> counts = new TreeMap<>();
            for (Following followingOne : following) {
                FollowerCount followerCount = followingOne.getGroups().get(group);
                counts.put(followerCount.getCount(), followerCount.getFollower());
            }
            int count = counts.keySet().iterator().next();
            Follower follower = counts.values().iterator().next();

            String accessToken = follower.getAccessToken();
            Set<Post> posts = apiService.requestPosts(group.getGroupInfo().getVkId(), count, accessToken);
            posts.forEach((v) -> {
                v.setGroup(group);
                Set<Long> likedUserIds = apiService.requestLikedUserIds(
                        group.getGroupInfo().getVkId(),
                        v.getVkId(),
                        accessToken
                );
                v.setLikedUserIds(likedUserIds);
            });

            group.setPosts(posts);
            accountService.updateGroup(group);
        }

        Set<Following> following =  accountService.getAllFollowing();
        for (Following followingOne : following) {
            Set<Group> groupsOfFollowing = followingOne.getGroups().keySet();
            Set<Group> groupsWithLikedPosts = appLogic.filterGroupsByFollowingLike(
                    groupsOfFollowing,
                    followingOne.getUserInfo().getVkId()
            );
            Set<Post> likedPosts = new HashSet<>();
            groupsWithLikedPosts.forEach(g -> likedPosts.addAll(g.getPosts()));
            followingOne.setLikedPosts(likedPosts);
            accountService.updateFollowing(followingOne);
        }

        System.out.println("Ok");
    }


}
