package handler.snapshot_building;

import org.junit.Before;
import org.junit.Test;
import shared.controller.api_service.IApiService;
import shared.model.user.Follower;
import shared.model.user.Following;
import shared.model.user.User;
import shared.model.user.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class GroupSnapshotBuilderTest {
    private IApiService apiService;
    private Follower follower;
    private Following following;
    private UserInfo userInfo;
    private GroupSnapshotBuilder groupSnapshotBuilder;

    @Before
    public void setUp() {
        apiService = mock(IApiService.class);
        follower = mock(Follower.class);
        following = mock(Following.class);
        userInfo = mock(UserInfo.class);
        groupSnapshotBuilder = new GroupSnapshotBuilder(apiService);
    }

    @Test(expected = NullPointerException.class)
    public void testBuild() throws Exception {
        Long followingVkId = 1L;
        String accessToken = "accessToken";
        ArrayList<Long> groupIds = new ArrayList<>(Arrays.asList(1L, 2L));

        when(following.getUserInfo()).thenReturn(userInfo);
        when(userInfo.getVkId()).thenReturn(followingVkId);
        when(follower.getAccessToken()).thenReturn(accessToken);
        when(apiService.requestGroupIds(followingVkId, accessToken)).thenReturn(groupIds);
        when(apiService.requestGroups(groupIds)).thenReturn(null);

        groupSnapshotBuilder.build(following, follower);

        verify(apiService, times(1)).requestGroupIds(followingVkId, accessToken);
        verify(apiService, times(1)).requestGroups(groupIds);
    }
}