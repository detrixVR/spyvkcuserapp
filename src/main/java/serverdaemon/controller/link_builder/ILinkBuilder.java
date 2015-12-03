package serverdaemon.controller.link_builder;

import java.util.List;

/**
 * Created by aminought on 27.10.2015.
 */
public interface ILinkBuilder {
    String getRequestCodeLink();

    String getRequestAccessTokenLink(String code);

    String getUserInfoLink(Long userId, String accessToken);

    String getResolveScreenNameLink(String screenName);

    String getRequestGroupIdsLink(long followerId, String accessToken);

    String getRequestGroupsLink(List<Long> groupIds);

    String getRequestPostsLink(Long groupId, int count, String accessToken);

    String getRequestLikedUserIdsLink(Long groupId, Long postId, String accessToken);
}
