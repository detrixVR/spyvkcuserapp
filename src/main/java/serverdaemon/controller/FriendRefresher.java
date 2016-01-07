package serverdaemon.controller;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.friend.Friend;
import shared.model.snapshots.FriendListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.List;

public class FriendRefresher implements Refreshable<FriendListSnapshot> {
    private final IApiService apiService;
    private final IDBService dbService;

    public FriendRefresher(IApiService apiService, IDBService dbService) {
        this.apiService = apiService;
        this.dbService = dbService;
    }

    @Override
    public FriendListSnapshot refresh(Following following, Follower follower) {
        FriendListSnapshot friendListSnapshot = new FriendListSnapshot();
        List<Friend> friends = apiService.requestFriends(following.getUserInfo().getVkId(), follower.getAccessToken());

        friendListSnapshot.setFriendList(friends);
        friendListSnapshot.setSnapshotDate(System.currentTimeMillis());

        return friendListSnapshot;
    }
}
