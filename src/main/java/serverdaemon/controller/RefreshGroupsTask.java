package serverdaemon.controller;

import com.google.inject.Inject;
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

    @Inject
    private RefreshGroupsTask(IAccountService accountService, IApiService apiService) {
        this.accountService = accountService;
        this.apiService = apiService;
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
        System.out.println("Ok");
    }


}
