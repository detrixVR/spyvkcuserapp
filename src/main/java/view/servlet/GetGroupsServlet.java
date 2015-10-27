package view.servlet;

import com.google.inject.Inject;
import controller.account_service.IAccountService;
import controller.api_service.IApiService;
import controller.cookies_service.ICookiesService;
import model.group.GroupInfo;
import view.templater.IPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetGroupsServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;
    private ICookiesService cookiesService;
    private IPageGenerator pageGenerator;

    @Inject
    public GetGroupsServlet(IApiService apiService, IAccountService accountService, ICookiesService cookiesService,
                            IPageGenerator pageGenerator) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long followerId = Long.valueOf(req.getParameter("follower"));
        Long id = Long.valueOf(cookiesService.getCookie(req, "id"));
        String accessToken = accountService.getUser(id).getAccessToken();
        List<Long> groupIds = apiService.requestGroupIds(followerId, accessToken);
        List<GroupInfo> groupsInfo = apiService.requestGroupsInfo(groupIds);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("groupsInfo", groupsInfo);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(pageGenerator.getPage("groups.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}