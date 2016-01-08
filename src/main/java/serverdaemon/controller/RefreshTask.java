package serverdaemon.controller;

import com.google.inject.Inject;
import shared.controller.db_service.IDBService;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

public class RefreshTask extends TimerTask {
    private IDBService dbService;

    @Inject
    private RefreshTask(IDBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void run() {
        MessageProducer messageProducer = new MessageProducer();
        messageProducer.connect();

        Map<Long, Follower> followers = dbService.getAllFollowers();
        followers.forEach((id, follower) -> {
            Set<Following> followings = follower.getFollowing();
            followings.forEach(following -> {
                messageProducer.send(following, follower);
            });
        });
        messageProducer.close();
        System.out.println("Ok");
    }

}
