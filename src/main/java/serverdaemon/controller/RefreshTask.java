package serverdaemon.controller;

import com.google.inject.Inject;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.event.*;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.snapshots.Snapshot;
import shared.model.snapshots.VideoListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

public class RefreshTask extends TimerTask {
    private IApiService apiService;
    private IAppLogic appLogic;
    private IDBService dbService;

    @Inject
    private RefreshTask(IApiService apiService,
                        IAppLogic appLogic,
                        IDBService dbService) {
        this.apiService = apiService;
        this.appLogic = appLogic;
        this.dbService = dbService;
    }

    @Override
    public void run() {
//        Set<Group> groups = dbService.getAllGroups();
//        GroupRefresher groupRefresher = new GroupRefresher(apiService, dbService);
//        groupRefresher.refresh(groups);
//
//        GroupSnapshotBuilder groupSnapshotBuilder = new GroupSnapshotBuilder();
//        for (Group group : groups) {
//            GroupSnapshot groupSnapshot = groupSnapshotBuilder.build(group);
//            dbService.saveGroupSnapshot(groupSnapshot);
//        }
//
//        Set<Following> following = dbService.getAllFollowings();
//        FollowingRefresher followingRefresher = new FollowingRefresher(appLogic, dbService);
//        followingRefresher.refresh(following);
        GroupRefresher groupRefresher = new GroupRefresher(apiService, dbService);
        AudioRefresher audioRefresher = new AudioRefresher(apiService, dbService);
        VideoRefresher videoRefresher = new VideoRefresher(apiService, dbService);

        Map<Long, Follower> followers = dbService.getAllFollowers();
        followers.forEach((id, follower) -> {
            Set<Following> followings = follower.getFollowing();
            followings.forEach(following -> {

                FollowingEventTypes followingEventTypes = follower
                        .getFollowing_EventTypesList()
                        .stream()
                        .filter(following_eventTypes -> following_eventTypes.getFollowing() == following)
                        .findFirst()
                        .get();
                List<EventType> eventTypes = followingEventTypes.getEventTypes();

                FollowerEvents followerEvents = following
                        .getFollower_EventsList()
                        .stream()
                        .filter(follower_events -> follower_events.getFollower() == follower)
                        .findFirst()
                        .get();
                List<Snapshot> snapshots = followerEvents.getSnapshots();

                eventTypes.forEach(eventType -> {
                    switch (eventType) {
//                        case GROUP_LIKE:
//                            Snapshot snapshot = groupRefresher.refresh(following, follower);
//                            if(snapshots.size() == 0) {
//                                snapshots.add(snapshot);
//                            } else {
//                                GroupLikeSnapshotDifference groupLikeSnapshotDifference = new GroupLikeSnapshotDifference();
//                                List<Event> difference = groupLikeSnapshotDifference.difference(snapshots.get(0), snapshot);
//                                followerEvents.getEvents().addAll(difference);
//                            }
//                            break;
                        case AUDIO:
                            AudioListSnapshot snapshot = audioRefresher.refresh(following, follower);
                            if(snapshots.size() < followingEventTypes.getEventTypes().size()) {
                                snapshot.getAudioList().forEach(a -> dbService.saveAudio(a));
                                dbService.saveAudioListSnapshot(snapshot);
                                snapshots.add(snapshot);
                                dbService.updateFollowerEvents(followerEvents);
                                dbService.updateFollower(follower);
                                dbService.updateFollowing(following);
                            } else {
                                AudioSnapshotDifference audioSnapshotDifference = new AudioSnapshotDifference();
                                List<Event> difference = audioSnapshotDifference.difference(
                                        snapshots.get(0),
                                        snapshot,
                                        followerEvents.getEvents(),
                                        EventType.AUDIO
                                );
                                difference.forEach(event -> dbService.saveAudioEvent((AudioEvent) event));
                                followerEvents.getEvents().addAll(difference);
                                dbService.updateFollowerEvents(followerEvents);
                            }
                            break;
                        case VIDEO:
                            VideoListSnapshot videoListSnapshot = videoRefresher.refresh(following, follower);
                            if(snapshots.size() < followingEventTypes.getEventTypes().size()) {
                                videoListSnapshot.getVideoList().forEach(a -> dbService.saveVideo(a));
                                dbService.saveVideoListSnapshot(videoListSnapshot);
                                snapshots.add(videoListSnapshot);
                                dbService.updateFollowerEvents(followerEvents);
                                dbService.updateFollower(follower);
                                dbService.updateFollowing(following);
                            } else {
                                VideoSnapshotDifference videoSnapshotDifference = new VideoSnapshotDifference();
                                List<Event> difference = videoSnapshotDifference.difference(
                                        snapshots.get(1),
                                        videoListSnapshot,
                                        followerEvents.getEvents(),
                                        EventType.VIDEO
                                );
                                difference.forEach(event -> dbService.saveVideoEvent((VideoEvent) event));
                                followerEvents.getEvents().addAll(difference);
                                dbService.updateFollowerEvents(followerEvents);
                            }
                            break;
                    }
                });
            });
        });

        System.out.println("Ok");
    }
}
