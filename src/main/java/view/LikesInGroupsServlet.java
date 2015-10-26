package view;

import controller.AccountService;
import controller.ApiService;
import controller.CookiesService;
import controller.Logic;
import model.Group;
import model.GroupInfo;
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
    private PageGenerator pageGenerator;

    public LikesInGroupsServlet(ApiService apiService, AccountService accountService, CookiesService cookiesService,
                                Logic logic, PageGenerator pageGenerator) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.logic = logic;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = accountService.getUser(Long.valueOf(cookiesService.getCookie(req, "id")));

        String[] groupIds = req.getParameterValues("id");
        String[] groupNames = req.getParameterValues("name");
        String[] groupScreenNames = req.getParameterValues("screenName");
        String[] count = req.getParameterValues("count");
        ArrayList<GroupInfo> groupsInfo = logic.formGroupsInfoFromSources(groupIds, groupNames, groupScreenNames);
        ArrayList<Group> groups = new ArrayList<>();

        for (int i = 0; i < groupsInfo.size(); i++) {
            ArrayList<Post> posts = apiService.requestPosts(Long.valueOf(groupIds[i]), Integer.valueOf(count[i]),
                    user.getAccessToken());
            for (Post post : posts) {
                ArrayList<Long> userIds = apiService.requestLikedUserIds(Long.valueOf(groupIds[i]), post.getId()
                        , user.getAccessToken());
                post.setLikedUserIds(userIds);
            }
            groups.add(new Group(groupsInfo.get(i), posts));
        }

        Long follower = user.getFollowerIds().get(0);
        ArrayList<Group> groupsWithPostsLikedByUser = logic.filterGroupsByFollowerLike(groups, follower);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("groups", groupsWithPostsLikedByUser);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(pageGenerator.getPage("likedposts.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
