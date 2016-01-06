package shared.controller.api_service;

import com.google.inject.Inject;
import shared.controller.json_service.IJsonService;
import shared.controller.link_builder.ILinkBuilder;
import shared.controller.request.IRequest;
import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.UserInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        String answer;
        String accessToken;
        do {
            do {
                answer = request.get(requestAccessTokenLink, 0);
            } while(answer == null);
            accessToken = jsonService.getAccessToken(answer);
        } while (accessToken == null);
        return accessToken;
    }

    @Override
    public UserInfo getUserInfo(Long userId, String accessToken) {
        String getUserInfoLink = linkBuilder.getUserInfoLink(userId, accessToken);
        String userInfoString;
        UserInfo userInfo;
        do {
            userInfoString = request.get(getUserInfoLink, 0);
            userInfo = jsonService.getUserInfo(userInfoString);
        } while(userInfo == null);
        return userInfo;
    }

    @Override
    public Long resolveScreenName(String userLink) {
        String screenName = userLink.replace("https://vk.com/", "");
        String resolveScreenNameLink = linkBuilder.getResolveScreenNameLink(screenName);
        String answer;
        Long followingId;
        do {
            answer = request.get(resolveScreenNameLink, 0);
            followingId = jsonService.getFollowingId(answer);
        } while(followingId == null);
        return followingId;
    }

    @Override
    public ArrayList<Long> requestGroupIds(long followingId, String accessToken) {
        String requestGroupIdsLink = linkBuilder.getRequestGroupIdsLink(followingId, accessToken);
        String answer;
        ArrayList<Long> groupsIds;
        do {
            answer = request.get(requestGroupIdsLink, 0);
            groupsIds = jsonService.getGroupsIds(answer);
        } while(groupsIds == null);
        return groupsIds;
    }

    @Override
    public List<GroupInfo> requestGroupsInfo(List<Long> groupIds) {
        String requestGroupsLink = linkBuilder.getRequestGroupsLink(groupIds);
        String answer;
        ArrayList<GroupInfo> groupsInfo;
        do {
            answer = request.get(requestGroupsLink, 0);
            groupsInfo = jsonService.getGroupsInfo(answer);
        } while(groupsInfo == null);
        return groupsInfo;
    }

    @Override
    public Set<Post> requestPosts(Long groupId, Long addingDate, String accessToken) {
        Set<Post> posts = new HashSet<>();
//        final int maxCount = 10;
//        for(int i = 0; i < (int) Math.ceil((((double) addingDate)/((double)maxCount))); ++i) {
//            String requestPostsLink = linkBuilder.getRequestPostsLink(groupId, addingDate, i*maxCount, accessToken);
//            String answer;
//            Set<Post> result;
//            do {
//                answer = request.get(requestPostsLink, 200);
//                result = jsonService.getPosts(answer);
//            } while(result == null);
//            posts.addAll(result);
//        }
        final int count = 10;
        int i=0;
        final boolean[] isContinue = {true};
        do {
            String requestPostsLink = linkBuilder.getRequestPostsLink(groupId, count, i * count, accessToken);
            String answer;
            Set<Post> result;
            Set<Post> filterPosts = new HashSet<>();
            do {
                answer = request.get(requestPostsLink, 200);
                result = jsonService.getPosts(answer);
            } while(result == null);
            result.forEach(post -> {
                if(post.getDate() >= addingDate) {
                    filterPosts.add(post);
                } else {
                    isContinue[0] = false;
                }
            });
            posts.addAll(filterPosts);
            i++;
        } while (isContinue[0]);
        return posts;
    }

    @Override
    public Set<Long> requestLikedUserIds(Long groupId, Long postId, int count, String accessToken) {
        Set<Long> likedUserIds = new HashSet<>();
        final int maxCount = 1000;
        for(int i=0; i < (int) Math.ceil((((double)count)/((double)maxCount))); ++i) {
            String requestLikedUserIdsLink = linkBuilder.getRequestLikedUserIdsLink(groupId, postId, i*maxCount, accessToken);
            String answer;
            Set<Long> result;
            do {
                answer = request.get(requestLikedUserIdsLink, 200);
                result = jsonService.getLikedUserIds(answer);
            } while(result == null);
            likedUserIds.addAll(result);
        }
        return likedUserIds;
    }
}
