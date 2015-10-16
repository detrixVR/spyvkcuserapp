package view;

import controller.AccountService;
import controller.ApiService;
import controller.CookiesService;
import controller.Logic;
import model.Group;
import model.Post;
import model.User;
import view.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LikesInGroupsServlet extends HttpServlet {
    private ApiService apiService;
    private AccountService accountService;
    private CookiesService cookiesService;
    private Logic logic;

    public LikesInGroupsServlet(ApiService apiService, AccountService accountService, CookiesService cookiesService, Logic logic) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.logic = logic;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = accountService.getUser(Long.valueOf(cookiesService.getCookie(req, "id")));

        String[] groupIds = req.getParameterValues("id");
        String[] groupNames = req.getParameterValues("name");
        String[] groupScreenNames = req.getParameterValues("screenName");
        String[] count = req.getParameterValues("count");
        ArrayList<Group> groups = logic.formGroupsFromSources(groupIds, groupNames, groupScreenNames);

        for (int i = 0; i < groups.size(); i++) {
            ArrayList<Post> posts = apiService.requestPosts(Long.valueOf(groupIds[i]), Integer.valueOf(count[i]),
                    user.getAccessToken());
            for (Post post : posts) {
                ArrayList<Long> userIds = apiService.requestLikedUserIds(Long.valueOf(groupIds[i]), post.getId()
                        , user.getAccessToken());
                post.setLikedUserIds(userIds);
            }
            groups.get(i).setPosts(posts);
        }

        Long followed = user.getFollowedIds().get(0);
        ArrayList<Group> groupsWithPostsLikedByUser = logic.findGroupsWithPostsLikedByUser(groups, followed);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("groups", groupsWithPostsLikedByUser);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(PageGenerator.getPage("likedposts.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
