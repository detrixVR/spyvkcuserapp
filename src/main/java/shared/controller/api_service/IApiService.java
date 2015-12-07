package shared.controller.api_service;

import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IApiService {
    String getRequestCodeLink();

    String requestAccessToken(String code);

    UserInfo getUserInfo(Long userId, String accessToken);

    Long resolveScreenName(String userLink);

    ArrayList<Long> requestGroupIds(long followerId, String accessToken);

    List<GroupInfo> requestGroupsInfo(List<Long> groupIds);

    Set<Post> requestPosts(Long groupId, int count, String accessToken);

    Set<Long> requestLikedUserIds(Long groupId, Long postId, int count, String accessToken);
}
