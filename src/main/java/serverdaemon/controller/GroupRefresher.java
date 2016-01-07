package serverdaemon.controller;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.snapshots.GroupListSnapshot;
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
        List<Long> groupIds = apiService.requestGroupIds(following.getUserInfo().getVkId(), follower.getAccessToken());
        List<Group> groupList = apiService.requestGroups(groupIds);
        groupListSnapshot.setGroupList(groupList);
        groupListSnapshot.setSnapshotDate(System.currentTimeMillis());

        return groupListSnapshot;
    }
}
