package shared.controller.api_service;

import com.google.inject.Inject;
import shared.controller.json_service.IJsonService;
import shared.controller.link_builder.ILinkBuilder;
import shared.controller.request.IRequest;
import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.UserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApiServiceImpl implements IApiService {
    private ILinkBuilder linkBuilder;
    private IJsonService jsonService;
    private IRequest request;

    @Inject
    public ApiServiceImpl(IRequest request, ILinkBuilder linkBuilder, IJsonService jsonService) {
        this.request = request;
        this.linkBuilder = linkBuilder;
        this.jsonService = jsonService;
    }

    @Override
    public String getRequestCodeLink() {
        return linkBuilder.getRequestCodeLink();
    }

    @Override
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

    @Override
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

    @Override
    public Long resolveScreenName(String userLink) {
        String screenName = userLink.replace("https://vk.com/", "");
        String resolveScreenNameLink = linkBuilder.getResolveScreenNameLink(screenName);
        String answer = null;
        try {
            answer = request.get(resolveScreenNameLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getFollowingId(answer);
    }

    @Override
    public ArrayList<Long> requestGroupIds(long followingId, String accessToken) {
        String requestGroupIdsLink = linkBuilder.getRequestGroupIdsLink(followingId, accessToken);
        String answer = null;
        try {
            answer = request.get(requestGroupIdsLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonService.getGroupsIds(answer);
    }

    @Override
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

    @Override
    public Set<Post> requestPosts(Long groupId, int count, String accessToken) {
        Set<Post> posts = new HashSet<>();
        final int maxCount = 100;
        for(int i=0; i < (int) Math.ceil((((double)count)/((double)maxCount))); ++i) {
            String requestPostsLink = linkBuilder.getRequestPostsLink(groupId, count, i*maxCount, accessToken);
            String answer = null;
            try {
                answer = request.get(requestPostsLink);
            } catch (IOException e) {
                e.printStackTrace();
            }
            posts.addAll(jsonService.getPosts(answer));
        }
        return posts;
    }

    @Override
    public Set<Long> requestLikedUserIds(Long groupId, Long postId, int count, String accessToken) {
        Set<Long> likedUserIds = new HashSet<>();
        final int maxCount = 1000;
        for(int i=0; i < (int) Math.ceil((((double)count)/((double)maxCount))); ++i) {
            String requestLikedUserIdsLink = linkBuilder.getRequestLikedUserIdsLink(groupId, postId, i*maxCount, accessToken);
            String answer = null;
            try {
                answer = request.get(requestLikedUserIdsLink);
            } catch (IOException e) {
                e.printStackTrace();
            }
            likedUserIds.addAll(jsonService.getLikedUserIds(answer));
        }
        return likedUserIds;
    }
}
