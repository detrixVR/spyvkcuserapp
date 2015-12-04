package serverdaemon.controller;

import com.google.inject.Inject;
import javafx.concurrent.Task;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.IApiService;
import shared.model.post.Post;

import java.util.Set;

public class FindPostsWithLikes extends Task<Boolean> {
    private final IApiService apiService;
    private final IAccountService accountService;
    private IAppLogic appLogic;

    @Inject
    private FindPostsWithLikes(IApiService apiService, IAccountService accountService, IAppLogic appLogic) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.appLogic = appLogic;
    }

    @Override
    protected Boolean call() throws Exception {
        Set<Post> posts = accountService.getAllPosts();
//        appLogic.filterGroupsByFollowingLike()
        return true;
    }
}
