package handler.snapshot_building;

import handler.snapshot_building.FriendSnapshotBuilder;
import org.junit.Before;
import org.junit.Test;
import shared.controller.api_service.IApiService;
import shared.model.user.Follower;
import shared.model.user.Following;
import shared.model.user.UserInfo;

import static org.mockito.Mockito.*;

public class FriendSnapshotBuilderTest {
    private FriendSnapshotBuilder friendSnapshotBuilder;
    private IApiService apiService;
    private Following following;
    private Follower follower;
    private UserInfo userInfo;

    @Before
    public void setUp() {
        apiService = mock(IApiService.class);
        following = mock(Following.class);
        follower = mock(Follower.class);
        userInfo = mock(UserInfo.class);
        friendSnapshotBuilder = new FriendSnapshotBuilder(apiService);
    }

    @Test(expected = NullPointerException.class)
    public void testBuild() throws Exception {
        Long followingVkId = 1L;
        String accessToken = "accessToken";
        when(following.getUserInfo()).thenReturn(userInfo);
        when(userInfo.getVkId()).thenReturn(followingVkId);
        when(follower.getAccessToken()).thenReturn(accessToken);
        when(apiService.requestFriends(followingVkId, accessToken)).thenReturn(null);

        friendSnapshotBuilder.build(following, follower);
    }
}