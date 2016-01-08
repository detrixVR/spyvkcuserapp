package handler.snapshot_building;

import org.junit.Before;
import org.junit.Test;
import shared.controller.api_service.IApiService;
import shared.model.user.Follower;
import shared.model.user.Following;
import shared.model.user.UserInfo;
import static org.mockito.Mockito.*;

public class PostSnapshotBuilderTest {
    private PostSnapshotBuilder postSnapshotBuilder;
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
        postSnapshotBuilder = new PostSnapshotBuilder(apiService);
    }

    @Test(expected = NullPointerException.class)
    public void testBuild() throws Exception {
        Long followingVkId = 1L;
        String accessToken = "accessToken";
        when(following.getUserInfo()).thenReturn(userInfo);
        when(userInfo.getVkId()).thenReturn(followingVkId);
        when(follower.getAccessToken()).thenReturn(accessToken);
        when(apiService.requestPosts(followingVkId, 0L, accessToken)).thenReturn(null);

        postSnapshotBuilder.build(following, follower);
    }
}