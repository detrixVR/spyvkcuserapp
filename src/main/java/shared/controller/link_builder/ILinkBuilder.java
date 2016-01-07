package shared.controller.link_builder;

import java.util.List;

public interface ILinkBuilder {
    String getRequestCodeLink();

    String getRequestAccessTokenLink(String code);

    String getUserInfoLink(Long userId, String accessToken);

    String getResolveScreenNameLink(String screenName);

    String getRequestGroupIdsLink(long followerId, String accessToken);

    String getRequestGroupsLink(List<Long> groupIds);

    String getRequestPostsLink(Long groupId, int count, int offset, String accessToken);

    String getRequestLikedUserIdsLink(Long groupId, Long postId, int offset, String accessToken);

    String getRequestAudioLink(Long vkId, String accessToken);

    String getRequestVideoLink(Long vkId, String accessToken);
}
