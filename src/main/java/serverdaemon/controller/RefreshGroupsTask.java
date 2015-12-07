package serverdaemon.controller;

import com.google.inject.Inject;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.group.GroupSnapshot;
import shared.model.user.Following;

import java.util.Set;
import java.util.TimerTask;

public class RefreshGroupsTask extends TimerTask {
    private IApiService apiService;
    private IAppLogic appLogic;
    private IDBService dbService;

    @Inject
    private RefreshGroupsTask(IApiService apiService,
                              IAppLogic appLogic,
                              IDBService dbService) {
        this.apiService = apiService;
        this.appLogic = appLogic;
        this.dbService = dbService;
    }

    @Override
    public void run() {
        Set<Group> groups = dbService.getAllGroups();
        GroupRefresher groupRefresher = new GroupRefresher(apiService, dbService);
        groupRefresher.refresh(groups);

        GroupSnapshotBuilder groupSnapshotBuilder = new GroupSnapshotBuilder();
        for (Group group : groups) {
            GroupSnapshot groupSnapshot = groupSnapshotBuilder.build(group);
            dbService.saveGroupSnapshot(groupSnapshot);
        }

        Set<Following> following = dbService.getAllFollowings();
        FollowingRefresher followingRefresher = new FollowingRefresher(appLogic, dbService);
        followingRefresher.refresh(following);

        System.out.println("Ok");
    }
}
