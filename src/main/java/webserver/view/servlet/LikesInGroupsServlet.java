package webserver.view.servlet;

import com.google.inject.Inject;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.IApiService;
import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.user.Follower;
import shared.model.user.Following;
import webserver.controller.cookies_service.ICookiesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LikesInGroupsServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;
    private ICookiesService cookiesService;

    @Inject
    public LikesInGroupsServlet(IApiService apiService, IAccountService accountService, ICookiesService cookiesService) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Follower follower = accountService.getFollower(Long.valueOf(cookiesService.getCookie(req, "id")));

        Long followingId = Long.valueOf(req.getParameter("followingId"));
        List<Long> groupIds = Arrays
                .stream(req.getParameterValues("id"))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Integer> count = Arrays
                .stream(req.getParameterValues("count"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<GroupInfo> groupsInfo = apiService.requestGroupsInfo(groupIds);

        apiService.requestGroupsInfo(groupIds);
        Set<Group> groups = new HashSet<>();
        groupsInfo.forEach((info) -> {
            Group group = new Group(info);
            groups.add(group);
        });

        Following following = accountService.getFollowing(followingId);
        accountService.addGroupsToFollowing(following, groups, follower, count);
        System.out.println("Ok");

        resp.sendRedirect("result?following=" + followingId);
    }
}
