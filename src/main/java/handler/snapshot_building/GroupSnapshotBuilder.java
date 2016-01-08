package handler.snapshot_building;

import com.google.inject.Inject;
import shared.controller.api_service.IApiService;
import shared.model.group.Group;
import shared.model.snapshots.GroupListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.*;

public class GroupSnapshotBuilder implements SnapshotBuilder<GroupListSnapshot> {
    private final IApiService apiService;

    @Inject
    public GroupSnapshotBuilder(IApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public GroupListSnapshot build(Following following, Follower follower) {
        GroupListSnapshot groupListSnapshot = new GroupListSnapshot();
        List<Long> groupIds = apiService.requestGroupIds(following.getUserInfo().getVkId(), follower.getAccessToken());
        List<Group> groupList = apiService.requestGroups(groupIds);
        if(groupList == null) throw new NullPointerException();
        groupListSnapshot.setGroupList(groupList);
        groupListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return groupListSnapshot;
    }
}
