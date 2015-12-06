package serverdaemon.controller;

import com.google.inject.Inject;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
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

        Set<Following> following = dbService.getAllFollowings();
        FollowingRefresher followingRefresher = new FollowingRefresher(appLogic, dbService);
        followingRefresher.refresh(following);

        System.out.println("Ok");
    }
}
