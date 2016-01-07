package webserver.view.servlet;

import com.google.inject.Inject;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import webserver.controller.cookies_service.ICookiesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LikesInGroupsServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;
    private ICookiesService cookiesService;
    private IDBService dbService;

    @Inject
    public LikesInGroupsServlet(IApiService apiService,
                                IAccountService accountService,
                                ICookiesService cookiesService,
                                IDBService dbService) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.dbService = dbService;
    }

//    private void addGroupsToFollowing(Following following, Set<Group> groups, Follower follower, List<Integer> counts) {
//        Iterator<Group> groupIterator = groups.iterator();
//        Iterator<Integer> countIterator = counts.iterator();
//        while (groupIterator.hasNext() || countIterator.hasNext()) {
//            Group group = groupIterator.next();
//            Integer count = countIterator.next();
//
//            FollowerCount followerCount = new FollowerCount(follower, count);
//            dbService.saveFollowerCount(followerCount);
//
//            group.addFollowing(following);
//            dbService.saveGroup(group);
//
//            following.addGroup(group, followerCount);
//            dbService.updateFollowing(following);
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Follower follower = accountService.getFollower(Long.valueOf(cookiesService.getCookie(req, "id")));
//
//        Long followingId = Long.valueOf(req.getParameter("followingId"));
//        List<Long> groupIds = Arrays
//                .stream(req.getParameterValues("id"))
//                .map(Long::parseLong)
//                .collect(Collectors.toList());
//
//        List<Integer> count = Arrays
//                .stream(req.getParameterValues("count"))
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//        List<GroupInfo> groupsInfo = apiService.requestGroupsInfo(groupIds);
//
//        apiService.requestGroupsInfo(groupIds);
//        Set<Group> groups = new HashSet<>();
//        groupsInfo.forEach((info) -> {
//            Group group = new Group(info);
//            groups.add(group);
//        });
//
//        Following following = follower.getFollowingByVkId(followingId);
//        addGroupsToFollowing(following, groups, follower, count);
//
//        System.out.println("Ok");
//
//        resp.sendRedirect("result?following=" + followingId);
    }
}
