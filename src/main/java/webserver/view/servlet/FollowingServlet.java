package webserver.view.servlet;

import com.google.inject.Inject;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.IApiService;
import shared.model.group.GroupInfo;
import webserver.controller.cookies_service.ICookiesService;
import webserver.view.templater.IPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowingServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;
    private ICookiesService cookiesService;
    private IPageGenerator pageGenerator;

    @Inject
    public FollowingServlet(IApiService apiService, IAccountService accountService, ICookiesService cookiesService,
                            IPageGenerator pageGenerator) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long followingId = Long.valueOf(req.getParameter("following"));
        Long followerId = Long.valueOf(cookiesService.getCookie(req, "id"));
        String accessToken = accountService.getFollower(followerId).getAccessToken();
        List<Long> groupIds = apiService.requestGroupIds(followingId, accessToken);
        List<GroupInfo> groupsInfo = apiService.requestGroupsInfo(groupIds);

        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("followingId", followingId);
        pageVariables.put("groupsInfo", groupsInfo);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(pageGenerator.getPage("following.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
