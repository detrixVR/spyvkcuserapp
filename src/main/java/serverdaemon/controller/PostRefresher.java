package serverdaemon.controller;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.post.Post;
import shared.model.snapshots.PostListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.List;
import java.util.stream.Collectors;

public class PostRefresher implements Refreshable<PostListSnapshot> {
    private final IApiService apiService;
    private final IDBService dbService;

    public PostRefresher(IApiService apiService, IDBService dbService) {
        this.apiService = apiService;
        this.dbService = dbService;
    }

    @Override
    public PostListSnapshot refresh(Following following, Follower follower) {
        PostListSnapshot postListSnapshot = new PostListSnapshot();
        List<Post> posts = apiService.requestPosts(following.getUserInfo().getVkId(), 0L, follower.getAccessToken())
                .stream().collect(Collectors.toList());

        postListSnapshot.setPostList(posts);
        postListSnapshot.setSnapshotDate(System.currentTimeMillis());

        return postListSnapshot;
    }
}
