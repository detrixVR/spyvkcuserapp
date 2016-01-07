package serverdaemon.controller;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.event.FollowerEvents;
import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.snapshots.GroupListSnapshot;
import shared.model.snapshots.GroupSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.*;

public class GroupRefresher implements Refreshable<GroupListSnapshot> {
    private final IApiService apiService;
    private final IDBService dbService;

    public GroupRefresher(IApiService apiService, IDBService dbService) {
        this.apiService = apiService;
        this.dbService = dbService;
    }

    @Override
    public GroupListSnapshot refresh(Following following, Follower follower) {
        GroupListSnapshot groupListSnapshot = new GroupListSnapshot();
        List<GroupSnapshot> groupSnapshots = groupListSnapshot.getGroupSnapshots();

        FollowerEvents followerEvents = following
                .getFollower_EventsList()
                .stream()
                .filter(follower_events -> follower_events.getFollower() == follower)
                .findFirst()
                .get();
        Long addingDate = followerEvents.getAddingDate();
        ArrayList<Long> groupIds = apiService.requestGroupIds(
                following.getUserInfo().getVkId(),
                follower.getAccessToken()
        );
        List<GroupInfo> groupsInfo = apiService.requestGroupsInfo(groupIds);
        groupsInfo.forEach(groupInfo -> {
            Group group = new Group(groupInfo);
            Set<Post> posts = apiService.requestPosts(
                    group.getGroupInfo().getVkId(),
                    addingDate,
                    follower.getAccessToken()
            );
            group.setPosts(posts);

            GroupSnapshot groupSnapshot = new GroupSnapshot();
            groupSnapshot.setGroup(group);
            groupSnapshot.setSnapshotDate(System.currentTimeMillis());
            groupSnapshots.add(groupSnapshot);
        });
        groupListSnapshot.setSnapshotDate(System.currentTimeMillis());
        return groupListSnapshot;
    }

    //    public void refresh(Set<Group> groups) {
//        for (Group group : groups) {
//            Set<Following> following = group.getFollowing();
//            Map<Integer, Follower> counts = new TreeMap<>();
//            for (Following followingOne : following) {
//                FollowerCount followerCount = followingOne.getGroups().get(group);
//                counts.put(followerCount.getCount(), followerCount.getFollower());
//            }
//            int count = counts.keySet().iterator().next();
//            Follower follower = counts.values().iterator().next();
//
//            String accessToken = follower.getAccessToken();
//            Set<Post> posts = apiService.requestPosts(group.getGroupInfo().getVkId(), count, accessToken);
//            long l = System.currentTimeMillis();
//            posts.forEach((v) -> {
//                v.setGroup(group);
//                Set<Long> likedUserIds = apiService.requestLikedUserIds(
//                        group.getGroupInfo().getVkId(),
//                        v.getVkId(),
//                        v.getLikesCount(),
//                        accessToken
//                );
//                v.setLikedUserIds(likedUserIds);
//            });
//            System.out.println((System.currentTimeMillis() - l)/1000);
//
//            group.setPosts(posts);
//            dbService.updateGroup(group);
//        }
//    }
}
