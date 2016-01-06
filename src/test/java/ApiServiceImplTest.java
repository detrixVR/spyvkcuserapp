import shared.controller.api_service.ApiServiceImpl;
import shared.controller.api_service.IApiService;
import shared.controller.json_service.IJsonService;
import shared.controller.link_builder.ILinkBuilder;
import shared.controller.request.IRequest;
import shared.model.post.Post;
import shared.model.user.UserInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApiServiceImplTest {
    private ILinkBuilder linkBuilder;
    private IJsonService jsonService;
    private IRequest request;
    private IApiService apiService;

    @Before
    public void setUp() {
        linkBuilder = mock(ILinkBuilder.class);
        jsonService = mock(IJsonService.class);
        request = mock(IRequest.class);
        apiService = new ApiServiceImpl(request, linkBuilder, jsonService);
    }

    @Test
    public void testGetRequestCodeLink() throws Exception {
        apiService.getRequestCodeLink();

        verify(linkBuilder, times(1)).getRequestCodeLink();
    }

    @Test
    public void testRequestAccessToken() throws Exception {
        String code = "code";
        String requestAccessTokenLink = "accessTokenLink";
        when(linkBuilder.getRequestAccessTokenLink(code)).thenReturn(requestAccessTokenLink);
        String answer = "answer";
        when(request.get(requestAccessTokenLink, 0)).thenReturn(answer);
        String accessToken = "accessToken";
        when(jsonService.getAccessToken(answer)).thenReturn(accessToken);

        String actualAccessToken = apiService.requestAccessToken(code);

        verify(linkBuilder, times(1)).getRequestAccessTokenLink(code);
        verify(request, times(1)).get(requestAccessTokenLink, 0);
        verify(jsonService, times(1)).getAccessToken(answer);
        assertEquals(accessToken, actualAccessToken);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        Long userId = 1l;
        String accessToken = "accessToken";
        String userInfoLink = "userInfoLink";
        when(linkBuilder.getUserInfoLink(userId, accessToken)).thenReturn(userInfoLink);
        String userInfoString = "userInfoString";
        when(request.get(userInfoLink, 0)).thenReturn(userInfoString);
        when(jsonService.getUserInfo(userInfoString)).thenReturn(new UserInfo());

        apiService.getUserInfo(userId, accessToken);

        verify(linkBuilder, times(1)).getUserInfoLink(userId, accessToken);
        verify(request, times(1)).get(userInfoLink, 0);
        verify(jsonService, times(1)).getUserInfo(userInfoString);
    }

    @Test
    public void testResolveScreenName() throws Exception {
        String userLink = "https://vk.com/durov";
        String resolveScreenNameLink = "resolveScreenNameLink";
        String screenName = "durov";
        when(linkBuilder.getResolveScreenNameLink(screenName)).thenReturn(resolveScreenNameLink);
        String answer = "answer";
        when(request.get(resolveScreenNameLink, 0)).thenReturn(answer);
        Long id = 1L;
        when(jsonService.getFollowingId(answer)).thenReturn(id);

        Long actualId = apiService.resolveScreenName(userLink);

        verify(linkBuilder, times(1)).getResolveScreenNameLink(screenName);
        verify(request, times(1)).get(resolveScreenNameLink, 0);
        verify(jsonService, times(1)).getFollowingId(answer);
        assertEquals(id, actualId);
    }

    @Test
    public void testRequestGroupIds() throws Exception {
        long followerId = 1L;
        String accessToken = "accessToken";
        String requestGroupIdsLink = "requestGroupIdsLink";
        when(linkBuilder.getRequestGroupIdsLink(followerId, accessToken)).thenReturn(requestGroupIdsLink);
        String answer = "answer";
        when(request.get(requestGroupIdsLink, 0)).thenReturn(answer);
        when(jsonService.getGroupsIds(answer)).thenReturn(new ArrayList<>());

        apiService.requestGroupIds(followerId, accessToken);

        verify(linkBuilder, times(1)).getRequestGroupIdsLink(followerId, accessToken);
        verify(request, times(1)).get(requestGroupIdsLink, 0);
        verify(jsonService, times(1)).getGroupsIds(answer);
    }

    @Test
    public void testRequestGroupsInfo() throws Exception {
        List<Long> groupIds = Arrays.asList(1L, 2L, 3L);
        String requestGroupsLink = "requestGroupsLink";
        when(linkBuilder.getRequestGroupsLink(groupIds)).thenReturn(requestGroupsLink);
        String answer = "answer";
        when(request.get(requestGroupsLink, 0)).thenReturn(answer);
        when(jsonService.getGroupsInfo(answer)).thenReturn(new ArrayList<>());

        apiService.requestGroupsInfo(groupIds);

        verify(linkBuilder, times(1)).getRequestGroupsLink(groupIds);
        verify(request, times(1)).get(requestGroupsLink, 0);
        verify(jsonService, times(1)).getGroupsInfo(answer);
    }

    @Test
    public void testRequestPosts() throws Exception {
        Long groupId = 1L;
        int count = 1;
        int offset = 0;
        String accessToken = "accessToken";
        String requestPostsLink = "requestPostsLink";
        when(linkBuilder.getRequestPostsLink(groupId, count, offset, accessToken)).thenReturn(requestPostsLink);
        String answer = "answer";
        when(request.get(requestPostsLink, 200)).thenReturn(answer);
        when(jsonService.getPosts(answer)).thenReturn(new LinkedHashSet<>());

//        apiService.requestPosts(groupId, count, accessToken);

        verify(linkBuilder, times(1)).getRequestPostsLink(groupId, count, offset, accessToken);
        verify(request, times(1)).get(requestPostsLink, 200);
        verify(jsonService, times(1)).getPosts(answer);
    }

    @Test
    public void testRequestLikedUserIds() throws Exception {
        Long groupId = 1L;
        Long postId = 1L;
        int count = 1;
        int offset = 0;
        String accessToken = "accessToken";
        String requestLikedUserIdsLink = "requestLikedUserIdsLink";
        when(linkBuilder.getRequestLikedUserIdsLink(groupId, postId, offset, accessToken)).thenReturn(requestLikedUserIdsLink);
        String answer = "answer";
        when(request.get(requestLikedUserIdsLink, 200)).thenReturn(answer);
        when(jsonService.getLikedUserIds(answer)).thenReturn(new HashSet<>());

        apiService.requestLikedUserIds(groupId, postId, count, accessToken);

        verify(linkBuilder, times(1)).getRequestLikedUserIdsLink(groupId, postId, offset, accessToken);
        verify(request, times(1)).get(requestLikedUserIdsLink, 200);
        verify(jsonService, times(1)).getLikedUserIds(answer);
    }
}