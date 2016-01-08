package handler.snapshot_building;

import com.google.inject.Inject;
import shared.controller.api_service.IApiService;
import shared.model.post.Post;
import shared.model.snapshots.PostListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.List;
import java.util.stream.Collectors;

public class PostSnapshotBuilder implements SnapshotBuilder<PostListSnapshot> {
    private final IApiService apiService;

    @Inject
    public PostSnapshotBuilder(IApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public PostListSnapshot build(Following following, Follower follower) {
        PostListSnapshot postListSnapshot = new PostListSnapshot();
        List<Post> posts = apiService.requestPosts(following.getUserInfo().getVkId(), 0L, follower.getAccessToken())
                .stream().collect(Collectors.toList());
        postListSnapshot.setPostList(posts);
        postListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return postListSnapshot;
    }
}
