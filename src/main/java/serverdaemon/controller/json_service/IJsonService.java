package serverdaemon.controller.json_service;

import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.UserInfo;

import java.util.ArrayList;

public interface IJsonService {
    String getAccessToken(String answer);

    UserInfo getUserInfo(String userInfoString);

    Long getFollowingId(String answer);

    ArrayList<Long> getGroupsIds(String answer);

    ArrayList<GroupInfo> getGroupsInfo(String answer);

    ArrayList<Post> getPosts(String answer);

    ArrayList<Long> getLikedUserIds(String answer);

    String getClientSecret(String valuesJson);
}
