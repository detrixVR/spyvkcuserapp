package shared.controller.link_builder;

import shared.controller.json_service.JsonServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LinkBuilderImpl implements ILinkBuilder {
    private int clientId = 5103937;
    private String redirectUri;

    private String clientSecret = null;

    public LinkBuilderImpl() {
        redirectUri = "http://localhost:8080/login";
        String pathToValuesJson = getClass().getResource("/values.json").getPath();
        pathToValuesJson = pathToValuesJson.substring(1);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(pathToValuesJson));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder valuesJsonBuilder = new StringBuilder();
        lines.forEach(valuesJsonBuilder::append);
        String valuesJson = valuesJsonBuilder.toString();
        clientSecret = (new JsonServiceImpl()).getClientSecret(valuesJson);
    }

    @Override
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

    @Override
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

    @Override
    public String getUserInfoLink(Long userId, String accessToken) {
        StringBuilder userInfoLink = new StringBuilder();
        userInfoLink
                .append("https://api.vk.com/method/users.get?")
                .append(userId != null ? ("user_ids=" + userId + "&") : "")
                .append("v=5.37")
                .append("&access_token=")
                .append(accessToken);
        return userInfoLink.toString();
    }

    @Override
    public String getResolveScreenNameLink(String screenName) {
        StringBuilder resolveScreenNameLink = new StringBuilder();
        resolveScreenNameLink.
                append("https://api.vk.com/method/utils.resolveScreenName?")
                .append("screen_name=")
                .append(screenName)
                .append("&v=5.37");
        return resolveScreenNameLink.toString();
    }

    @Override
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

    @Override
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

    @Override
    public String getRequestPostsLink(Long groupId, int count, int offset, String accessToken) {
        StringBuilder requestPostsLink = new StringBuilder();
        requestPostsLink
                .append("https://api.vk.com/method/wall.get?")
                .append("owner_id=-")
                .append(groupId)
                .append("&offset=")
                .append(offset)
                .append("&count=")
                .append(count)
                .append("&v=5.37")
                .append("&access_token=")
                .append(accessToken);
        return requestPostsLink.toString();
    }

    @Override
    public String getRequestLikedUserIdsLink(Long groupId, Long postId, int offset, String accessToken) {
        StringBuilder requestLikedUserIdsLink = new StringBuilder();
        requestLikedUserIdsLink
                .append("https://api.vk.com/method/likes.getList?")
                .append("type=post")
                .append("&owner_id=-")
                .append(groupId)
                .append("&item_id=")
                .append(postId)
                .append("&offset=")
                .append(offset)
                .append("&count=1000")
                .append("&v=5.37")
                .append("&access_token=")
                .append(accessToken);
        return requestLikedUserIdsLink.toString();
    }
}
