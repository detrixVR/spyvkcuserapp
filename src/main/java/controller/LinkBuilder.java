package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LinkBuilder {
    private int clientId = 5103937;
    private String redirectUri = "http://localhost:8080/vkchase/login";
    private String clientSecret = null;

    public LinkBuilder() {
        String path = getClass().getResource("/values.json").getPath();
        path = path.substring(1);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder valuesJsonBuilder = new StringBuilder();
        lines.forEach(valuesJsonBuilder::append);
        String valuesJson = valuesJsonBuilder.toString();
        clientSecret = (new JsonService()).getClientSecret(valuesJson);
    }

    public String getRequestCodeLink() {
        StringBuilder requestCodeLink = new StringBuilder();
        requestCodeLink
                .append("https://oauth.vk.com/authorize?")
                .append("client_id=")
                .append(clientId)
                .append("&redirect_uri=")
                .append(redirectUri)
                .append("&display=popup")
                .append("&scope=groups,offline")
                .append("&response_type=code")
                .append("&v=5.37");
        return requestCodeLink.toString();
    }

    public String getRequestAccessTokenLink(String code) {
        StringBuilder requestAccessTokenLink = new StringBuilder();
        requestAccessTokenLink
                .append("https://oauth.vk.com/access_token?")
                .append("client_id=")
                .append(clientId)
                .append("&client_secret=")
                .append(clientSecret)
                .append("&redirect_uri=")
                .append(redirectUri)
                .append("&code=")
                .append(code);
        return requestAccessTokenLink.toString();
    }

    public String getUserInfoLink(Long userId, String accessToken) {
        StringBuilder userInfoLink = new StringBuilder();
        userInfoLink
                .append("https://api.vk.com/method/users.get?")
                .append("v=5.37")
                .append("&access_token=")
                .append(accessToken);
        return userInfoLink.toString();
    }

    public String getResolveScreenNameLink(String screenName) {
        StringBuilder resolveScreenNameLink = new StringBuilder();
        resolveScreenNameLink.
                append("https://api.vk.com/method/utils.resolveScreenName?")
                .append("screen_name=")
                .append(screenName)
                .append("&v=5.37");
        return resolveScreenNameLink.toString();
    }

    public String getRequestGroupIdsLink(long followerId, String accessToken) {
        StringBuilder requestGroupIdsLink = new StringBuilder();
        requestGroupIdsLink
                .append("https://api.vk.com/method/groups.get?")
                .append("user_id=")
                .append(followerId)
                .append("&count=1000")
                .append("&v=5.37")
                .append("&access_token=")
                .append(accessToken);
        return requestGroupIdsLink.toString();
    }

    public String getRequestGroupsLink(List<Long> groupIds) {
        StringBuilder requestGroupsLink = new StringBuilder();
        requestGroupsLink
                .append("https://api.vk.com/method/groups.getById?")
                .append("group_ids=");
        for (Long groupId : groupIds) {
            requestGroupsLink.append(groupId);
            requestGroupsLink.append(",");
        }
        requestGroupsLink.append("&v=5.37");
        return requestGroupsLink.toString();
    }

    public String getRequestPostsLink(Long groupId, int count) {
        StringBuilder requestPostsLink = new StringBuilder();
        requestPostsLink
                .append("https://api.vk.com/method/wall.get?")
                .append("owner_id=-")
                .append(groupId)
                .append("&offset=0")
                .append("&count=")
                .append(count)
                .append("&v=5.37");
        return requestPostsLink.toString();
    }

    public String getRequestLikedUserIdsLink(Long groupId, Long postId) {
        StringBuilder requestLikedUserIdsLink = new StringBuilder();
        requestLikedUserIdsLink
                .append("https://api.vk.com/method/likes.getList?")
                .append("type=post")
                .append("&owner_id=-")
                .append(groupId)
                .append("&item_id=")
                .append(postId)
                .append("&offset=0")
                .append("&count=1000");
        return requestLikedUserIdsLink.toString();
    }
}
