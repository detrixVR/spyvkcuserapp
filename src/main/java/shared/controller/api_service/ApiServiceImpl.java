package shared.controller.api_service;

import com.google.inject.Inject;
import shared.controller.json_service.IJsonService;
import shared.controller.link_builder.ILinkBuilder;
import shared.controller.request.IRequest;
import shared.model.audio.Audio;
import shared.model.friend.Friend;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.UserInfo;
import shared.model.video.Video;

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
            userInfoString = request.get(getUserInfoLink, 200);
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
    public List<Group> requestGroups(List<Long> groupIds) {
        String requestGroupsLink = linkBuilder.getRequestGroupsLink(groupIds);
        String answer;
        ArrayList<Group> groups;
        do {
            answer = request.get(requestGroupsLink, 200);
            groups = jsonService.getGroups(answer);
        } while(groups == null);
        return groups;
    }

    @Override
    public Set<Post> requestPosts(Long groupId, Long addingDate, String accessToken) {
        Set<Post> posts = new HashSet<>();
        final int count = 100;
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
            if(result.size() < 10) {
                isContinue[0] = false;
            }
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

    @Override
    public List<Audio> requestAudio(Long vkId, String accessToken) {
        List<Audio> audio;
        String requestAudioLink = linkBuilder.getRequestAudioLink(vkId, accessToken);
        String answer;
        do {
            answer = request.get(requestAudioLink, 200);
            audio = jsonService.getAudio(answer);
        } while (audio == null);
        return audio;
    }

    @Override
    public List<Video> requestVideo(Long vkId, String accessToken) {
        List<Video> video;
        String requestVideoLink = linkBuilder.getRequestVideoLink(vkId, accessToken);
        String answer;
        do {
            answer = request.get(requestVideoLink, 250);
            video = jsonService.getVideo(answer);
        } while (video == null);
        return video;
    }

    @Override
    public List<Friend> requestFriends(Long vkId, String accessToken) {
        List<Friend> friends;
        String requestFriendsLink = linkBuilder.getRequestFriendsLink(vkId, accessToken);
        String answer;
        do {
            answer = request.get(requestFriendsLink, 200);
            friends = jsonService.getFriends(answer);
        } while (friends == null);
        return friends;
    }
}
