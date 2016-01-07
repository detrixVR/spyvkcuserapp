package serverdaemon.controller.snapshot_building;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.friend.Friend;
import shared.model.snapshots.FriendListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.List;

public class FriendSnapshotBuilder implements SnapshotBuilder<FriendListSnapshot> {
    private final IApiService apiService;

    public FriendSnapshotBuilder(IApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public FriendListSnapshot build(Following following, Follower follower) {
        FriendListSnapshot friendListSnapshot = new FriendListSnapshot();
        List<Friend> friends = apiService.requestFriends(following.getUserInfo().getVkId(), follower.getAccessToken());
        friendListSnapshot.setFriendList(friends);
        friendListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return friendListSnapshot;
    }
}
