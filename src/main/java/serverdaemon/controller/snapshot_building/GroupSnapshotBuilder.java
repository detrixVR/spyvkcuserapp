package serverdaemon.controller.snapshot_building;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.snapshots.GroupListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.*;

public class GroupSnapshotBuilder implements SnapshotBuilder<GroupListSnapshot> {
    private final IApiService apiService;

    public GroupSnapshotBuilder(IApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public GroupListSnapshot build(Following following, Follower follower) {
        GroupListSnapshot groupListSnapshot = new GroupListSnapshot();
        List<Long> groupIds = apiService.requestGroupIds(following.getUserInfo().getVkId(), follower.getAccessToken());
        List<Group> groupList = apiService.requestGroups(groupIds);
        groupListSnapshot.setGroupList(groupList);
        groupListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return groupListSnapshot;
    }
}
