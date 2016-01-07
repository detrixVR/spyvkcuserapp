package shared.controller.db_service;

import shared.model.audio.Audio;
import shared.model.event.AudioEvent;
import shared.model.event.Event;
import shared.model.event.FollowerEvents;
import shared.model.group.Group;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.snapshots.GroupSnapshot;
import shared.model.post.Post;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.Map;
import java.util.Set;

public interface IDBService {
    void saveFollower(Follower follower);

    void saveFollowing(Following following);

    void updateFollower(Follower follower);

    void saveAudio(Audio audio);

    void updateFollowing(Following following);

    Following getFollowingByVkId(Long id);

    void saveGroup(Group group);

    Set<Group> getAllGroups();

    Map<Long, Follower> getAllFollowers();

    void updateGroup(Group group);

    Set<Post> getAllPosts();

    Set<Following> getAllFollowings();

    Follower getFollowerByVkId(Long id);

    void saveGroupSnapshot(GroupSnapshot groupSnapshot);

    void saveAudioListSnapshot(AudioListSnapshot audioListSnapshot);

    void saveFollowerEvents(FollowerEvents followerEvents);

    void updateFollowerEvents(FollowerEvents followerEvents);

    void saveEvent(Event event);

    void saveAudioEvent(AudioEvent event);
}