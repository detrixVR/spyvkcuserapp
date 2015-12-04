package websever.view.servlet;

import com.google.inject.Inject;
import serverdaemon.controller.logic.IAppLogic;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.IApiService;
import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.user.Follower;
import shared.model.user.Following;
import websever.controller.cookies_service.ICookiesService;
import websever.view.templater.IPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LikesInGroupsServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;
    private ICookiesService cookiesService;
    private IAppLogic logic;
    private IPageGenerator pageGenerator;

    @Inject
    public LikesInGroupsServlet(IApiService apiService, IAccountService accountService, ICookiesService cookiesService,
                                IAppLogic logic, IPageGenerator pageGenerator) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.logic = logic;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Follower user = accountService.getFollower(Long.valueOf(cookiesService.getCookie(req, "id")));

        Long followingId = Long.valueOf(req.getParameter("followingId"));
        List<Long> groupIds = Arrays
                .stream(req.getParameterValues("id"))
                .map(Long::parseLong)
                .collect(Collectors.toList());
//        String[] groupNames = req.getParameterValues("name");
//        String[] groupScreenNames = req.getParameterValues("screenName");
        List<Integer> count = Arrays
                .stream(req.getParameterValues("count"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
//        ArrayList<GroupInfo> groupsInfo = logic.formGroupsInfoFromSources(groupIds, groupNames, groupScreenNames);
        List<GroupInfo> groupsInfo = apiService.requestGroupsInfo(groupIds);

        apiService.requestGroupsInfo(groupIds);
        Set<Group> groups = new HashSet<>();
        groupsInfo.forEach((info) ->  {
            Group group = new Group(info);
            groups.add(group);
        });

        Following following = accountService.getFollowing(followingId);
        accountService.addGroupsToFollowing(following, groups, count);
        System.out.println("Ok");

//        for (int i = 0; i < groupsInfo.size(); i++) {
//            Set<Post> posts = apiService.requestPosts(Long.valueOf(groupIds[i]), Integer.valueOf(count[i]),
//                    user.getAccessToken());
//            for (Post post : posts) {
//                Set<Long> userIds = apiService.requestLikedUserIds(Long.valueOf(groupIds[i]), post.getId()
//                        , user.getAccessToken());
//                post.setLikedUserIds(userIds);
//            }
//            groups.add(new Group(groupsInfo.get(i), posts));
//        }

//        Long follower = user.getFollowerIds().get(0);
//        Long following = 1L;
//        Set<Group> groupsWithPostsLikedByUser = logic.filterGroupsByFollowingLike(groups, following);

//        Map<String, Object> pageVariables = new HashMap<>();
//        pageVariables.put("groups", groupsWithPostsLikedByUser);
//        resp.setContentType("text/html; charset=utf-8");
//        resp.getWriter().println(pageGenerator.getPage("likedposts.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
