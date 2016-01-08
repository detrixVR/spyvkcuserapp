package handler;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import handler.snapshot_building.*;
import handler.snapshot_difference.*;
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

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class FollowingHandler {
    private final IDBService dbService;
    private final AudioSnapshotBuilder audioSnapshotBuilder;
    private final VideoSnapshotBuilder videoSnapshotBuilder;
    private final FriendSnapshotBuilder friendSnapshotBuilder;
    private final GroupSnapshotBuilder groupSnapshotBuilder;
    private final PostSnapshotBuilder postSnapshotBuilder;
    private Channel channel;
    private Connection conn;
    private QueueingConsumer consumer;

    @Inject
    public FollowingHandler(IDBService dbService,
                            AudioSnapshotBuilder audioSnapshotBuilder,
                            VideoSnapshotBuilder videoSnapshotBuilder,
                            FriendSnapshotBuilder friendSnapshotBuilder,
                            GroupSnapshotBuilder groupSnapshotBuilder,
                            PostSnapshotBuilder postSnapshotBuilder ) {
        this.dbService = dbService;
        this.audioSnapshotBuilder = audioSnapshotBuilder;
        this.videoSnapshotBuilder = videoSnapshotBuilder;
        this.friendSnapshotBuilder = friendSnapshotBuilder;
        this.groupSnapshotBuilder = groupSnapshotBuilder;
        this.postSnapshotBuilder = postSnapshotBuilder;
    }

    public void connect() {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            factory.setHost("localhost");
            factory.setPort(5672);
            conn = factory.newConnection();
            channel = conn.createChannel();
            String exchangeName = "exchange." + Math.abs((new Random()).nextInt());
            String queueName = "queue";
            String routingKey = "route";
            boolean durable = true;
            channel.exchangeDeclare(exchangeName, "topic", durable);
            channel.queueDeclare(queueName, durable, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            channel.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void processFollowings() {
        while(true) {
            QueueingConsumer.Delivery delivery;
            try {
                delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                Long tag = delivery.getEnvelope().getDeliveryTag();

                String[] split = message.split(" ");
                Long followerVkId = Long.valueOf(split[0]);
                Long followingVkId = Long.valueOf(split[1]);

                Follower follower = dbService.getFollowerByVkId(followerVkId);
                Following following = follower.getFollowingByVkId(followingVkId);

                refreshFollowing(follower, following);

                channel.basicAck(tag, false);
                System.out.println("Ok");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshFollowing(Follower follower, Following following) {
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
                    Process(audioSnapshotBuilder, follower, following, followingEventTypes, followerEvents, snapshots, i,
                            Audio.class, AudioListSnapshot.class, AudioEvent.class, new AudioSnapshotDifference(),
                            EventType.AUDIO);
                    break;
                case VIDEO:
                    Process(videoSnapshotBuilder, follower, following, followingEventTypes, followerEvents, snapshots, i,
                            Video.class, VideoListSnapshot.class, VideoEvent.class, new VideoSnapshotDifference(),
                            EventType.VIDEO);
                    break;
                case FRIEND:
                    Process(friendSnapshotBuilder, follower, following, followingEventTypes, followerEvents, snapshots, i,
                            Friend.class, FriendListSnapshot.class, FriendEvent.class, new FriendSnapshotDifference(),
                            EventType.FRIEND);
                    break;
                case GROUP:
                    Process(groupSnapshotBuilder, follower, following, followingEventTypes, followerEvents, snapshots, i,
                            Group.class, GroupListSnapshot.class, GroupEvent.class, new GroupSnapshotDifference(),
                            EventType.GROUP);
                    break;
                case POST:
                    Process(postSnapshotBuilder, follower, following, followingEventTypes, followerEvents, snapshots, i,
                            Post.class, PostListSnapshot.class, PostEvent.class, new PostSnapshotDifference(),
                            EventType.POST);
            }
        }
    }

    @SuppressWarnings(value = "unchecked")
    private <TypeOfSnapshot extends Snapshot,
            Refresher extends SnapshotBuilder,
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
        TypeOfSnapshot snapshot = (TypeOfSnapshot) refresher.build(following, follower);
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
