package shared.controller.json_service;

import shared.model.audio.Audio;
import shared.model.friend.Friend;
import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.UserInfo;
import shared.model.video.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IJsonService {
    String getAccessToken(String answer);

    UserInfo getUserInfo(String userInfoString);

    Long getFollowingId(String answer);

    ArrayList<Long> getGroupsIds(String answer);

    ArrayList<GroupInfo> getGroupsInfo(String answer);

    Set<Post> getPosts(String answer);

    Set<Long> getLikedUserIds(String answer);

    String getClientSecret(String valuesJson);

    List<Audio> getAudio(String answer);

    List<Video> getVideo(String answer);

    List<Friend> getFriends(String answer);
}
