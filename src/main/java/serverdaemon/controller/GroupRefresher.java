package serverdaemon.controller;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.Follower;
import shared.model.user.FollowerCount;
import shared.model.user.Following;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class GroupRefresher implements Refreshable<Group> {
    private final IApiService apiService;
    private final IDBService dbService;

    public GroupRefresher(IApiService apiService, IDBService dbService) {
        this.apiService = apiService;
        this.dbService = dbService;
    }

    public void refresh(Set<Group> groups) {
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
                        v.getLikesCount(),
                        accessToken
                );
                v.setLikedUserIds(likedUserIds);
            });

            group.setPosts(posts);
            dbService.updateGroup(group);
        }
    }
}
