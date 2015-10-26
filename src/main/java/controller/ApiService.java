package controller;

import com.google.inject.Inject;
import model.GroupInfo;
import model.Post;
import model.UserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private ILinkBuilder linkBuilder;
    private IJsonService jsonService;
    private IRequest request;

    @Inject
    public ApiService(IRequest request, ILinkBuilder linkBuilder, IJsonService jsonService) {
        this.request = request;
        this.linkBuilder = linkBuilder;
        this.jsonService = jsonService;
    }

    public String getRequestCodeLink() {
        return linkBuilder.getRequestCodeLink();
    }

    public String requestAccessToken(String code) {
        String requestAccessTokenLink = linkBuilder.getRequestAccessTokenLink(code);
        String answer = null;
        try {
            answer = request.get(requestAccessTokenLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String accessToken = jsonService.getAccessToken(answer);
        return accessToken;
    }

    public UserInfo getUserInfo(Long userId, String accessToken) {
        String getUserInfoLink = linkBuilder.getUserInfoLink(userId, accessToken);
        String userInfoString = null;
        try {
            userInfoString = request.get(getUserInfoLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getUserInfo(userInfoString);
    }

    public Long resolveScreenName(String userLink) {
        String screenName = userLink.replace("https://vk.com/", "");
        String resolveScreenNameLink = linkBuilder.getResolveScreenNameLink(screenName);
        String answer = null;
        try {
            answer = request.get(resolveScreenNameLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getFollowerId(answer);
    }

    public ArrayList<Long> requestGroupIds(long followerId, String accessToken) {
        String requestGroupIdsLink = linkBuilder.getRequestGroupIdsLink(followerId, accessToken);
        String answer = null;
        try {
            answer = request.get(requestGroupIdsLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getGroupsIds(answer);
    }

    public List<GroupInfo> requestGroupsInfo(List<Long> groupIds) {
        String requestGroupsLink = linkBuilder.getRequestGroupsLink(groupIds);
        String answer = null;
        try {
            answer = request.get(requestGroupsLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getGroupsInfo(answer);
    }

    public ArrayList<Post> requestPosts(Long groupId, int count, String accessToken) {
        String requestPostsLink = linkBuilder.getRequestPostsLink(groupId, count, accessToken);
        String answer = null;
        try {
            answer = request.get(requestPostsLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getPosts(answer);
    }

    public ArrayList<Long> requestLikedUserIds(Long groupId, Long postId, String accessToken) {
        String requestLikedUserIdsLink = linkBuilder.getRequestLikedUserIdsLink(groupId, postId, accessToken);
        String answer = null;
        try {
            answer = request.get(requestLikedUserIdsLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getLikedUserIds(answer);
    }
}
