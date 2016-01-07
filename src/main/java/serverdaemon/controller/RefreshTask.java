package serverdaemon.controller;

import com.google.inject.Inject;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.audio.Audio;
import shared.model.event.*;
import shared.model.friend.Friend;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.snapshots.*;
import shared.model.user.Follower;
import shared.model.user.Following;
import shared.model.video.Video;

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
        AudioRefresher audioRefresher = new AudioRefresher(apiService, dbService);
        VideoRefresher videoRefresher = new VideoRefresher(apiService, dbService);
        FriendRefresher friendRefresher = new FriendRefresher(apiService, dbService);
        GroupRefresher groupRefresher = new GroupRefresher(apiService, dbService);
        PostRefresher postRefresher = new PostRefresher(apiService, dbService);

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

                for (int i = 0; i < eventTypes.size(); i++) {
                    EventType eventType = eventTypes.get(i);
                    switch (eventType) {
                        case AUDIO:
                            Process(audioRefresher, follower, following, followingEventTypes, followerEvents, snapshots, i,
                                    Audio.class, AudioListSnapshot.class, AudioEvent.class, new AudioSnapshotDifference(),
                                    EventType.AUDIO);
                            break;
                        case VIDEO:
                            Process(videoRefresher, follower, following, followingEventTypes, followerEvents, snapshots, i,
                                    Video.class, VideoListSnapshot.class, VideoEvent.class, new VideoSnapshotDifference(),
                                    EventType.VIDEO);
                            break;
                        case FRIEND:
                            Process(friendRefresher, follower, following, followingEventTypes, followerEvents, snapshots, i,
                                    Friend.class, FriendListSnapshot.class, FriendEvent.class, new FriendSnapshotDifference(),
                                    EventType.FRIEND);
                            break;
                        case GROUP:
                            Process(groupRefresher, follower, following, followingEventTypes, followerEvents, snapshots, i,
                                    Group.class, GroupListSnapshot.class, GroupEvent.class, new GroupSnapshotDifference(),
                                    EventType.GROUP);
                            break;
                        case POST:
                            Process(postRefresher, follower, following, followingEventTypes, followerEvents, snapshots, i,
                                    Post.class, PostListSnapshot.class, PostEvent.class, new PostSnapshotDifference(),
                                    EventType.POST);
                    }
                }
            });
        });

        System.out.println("Ok");
    }

    @SuppressWarnings(value = "unchecked")
    private <TypeOfSnapshot extends Snapshot,
            Refresher extends Refreshable,
            TypeOfEvent extends Event,
            EventEntity> void Process(
            Refresher refresher,
            Follower follower,
            Following following,
            FollowingEventTypes followingEventTypes,
            FollowerEvents followerEvents,
            List<Snapshot> snapshots,
            int i,
            Class<EventEntity> eventEntityClass,
            Class<TypeOfSnapshot> typeOfSnapshotClass,
            Class<TypeOfEvent> typeOfEventClass,
            SnapshotDifference snapshotDifference,
            EventType eventType) {
        TypeOfSnapshot snapshot = (TypeOfSnapshot) refresher.refresh(following, follower);
        if (snapshots.size() < followingEventTypes.getEventTypes().size()) {
            snapshot.getList().forEach(a -> dbService.save((EventEntity) a, eventEntityClass));
            dbService.save(snapshot, typeOfSnapshotClass);
            snapshots.add(snapshot);
            dbService.updateFollowerEvents(followerEvents);
            dbService.updateFollower(follower);
            dbService.updateFollowing(following);
        } else {
            List<Event> differenceEvents = snapshotDifference.difference(
                    snapshots.get(i),
                    snapshot,
                    followerEvents.getEvents(),
                    eventType
            );
            differenceEvents.forEach(event -> dbService.save((TypeOfEvent) event, typeOfEventClass));
            followerEvents.getEvents().addAll(differenceEvents);
            dbService.updateFollowerEvents(followerEvents);
        }
    }
}
