package view;

import controller.AccountService;
import controller.ApiService;
import controller.CookiesService;
import model.Group;
import view.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetGroupsServlet extends HttpServlet {
    private ApiService apiService;
    private AccountService accountService;
    private CookiesService cookiesService;
    private PageGenerator pageGenerator;

    public GetGroupsServlet(ApiService apiService, AccountService accountService, CookiesService cookiesService,
                            PageGenerator pageGenerator) {
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
        List<Group> groups = apiService.requestGroups(groupIds);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("groups", groups);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(pageGenerator.getPage("groups.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
