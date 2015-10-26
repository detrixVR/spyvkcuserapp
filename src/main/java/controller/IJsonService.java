package controller;

import model.GroupInfo;
import model.Post;
import model.UserInfo;

import java.util.ArrayList;

public interface IJsonService {
    String getAccessToken(String answer);

    UserInfo getUserInfo(String userInfoString);

    Long getFollowerId(String answer);

    ArrayList<Long> getGroupsIds(String answer);

    ArrayList<GroupInfo> getGroupsInfo(String answer);

    ArrayList<Post> getPosts(String answer);

    ArrayList<Long> getLikedUserIds(String answer);

    String getClientSecret(String valuesJson);
}
