package webserver.view.servlet;

import com.google.inject.Inject;
import shared.controller.account_service.IAccountService;
import shared.controller.db_service.IDBService;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.Follower;
import shared.model.user.Following;
import webserver.controller.cookies_service.ICookiesService;
import webserver.view.templater.IPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ResultServlet extends HttpServlet {
    IAccountService accountService;
    private ICookiesService cookiesService;
    private IPageGenerator pageGenerator;
    private IDBService dbService;

    @Inject
    public ResultServlet(IAccountService accountService,
                         ICookiesService cookiesService,
                         IPageGenerator pageGenerator,
                         IDBService dbService) {
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.pageGenerator = pageGenerator;
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Follower follower = dbService.getFollowerByVkId(Long.valueOf(cookiesService.getCookie(req, "id")));
        Long followingId = Long.valueOf(req.getParameter("following"));

        Following following = follower.getFollowingByVkId(followingId);
        Set<Post> likedPosts = following.getLikedPosts();

        Map<Group, List<Post>> groupPostMap = new LinkedHashMap<>();
        for (Post likedPost : likedPosts) {
            if (!groupPostMap.containsKey(likedPost.getGroup())) {
                groupPostMap.put(
                        likedPost.getGroup(),
                        new LinkedList<>(Arrays.asList(new Post[]{likedPost}))
                );
            } else {
                groupPostMap.get(likedPost.getGroup()).add(likedPost);
            }
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("groupPostMap", groupPostMap);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(pageGenerator.getPage("result.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
