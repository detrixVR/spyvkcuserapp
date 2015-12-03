package serverdaemon.controller.api_service;

import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.UserInfo;

import java.util.ArrayList;
import java.util.List;

public interface IApiService {
    String getRequestCodeLink();

    String requestAccessToken(String code);

    UserInfo getUserInfo(Long userId, String accessToken);

    Long resolveScreenName(String userLink);

    ArrayList<Long> requestGroupIds(long followerId, String accessToken);

    List<GroupInfo> requestGroupsInfo(List<Long> groupIds);

    ArrayList<Post> requestPosts(Long groupId, int count, String accessToken);

    ArrayList<Long> requestLikedUserIds(Long groupId, Long postId, String accessToken);
}
