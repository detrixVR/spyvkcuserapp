package webserver.view.servlet;

import com.google.inject.Inject;
import shared.controller.account_service.IAccountService;
import shared.model.user.Follower;
import shared.model.user.Following;
import webserver.controller.cookies_service.ICookiesService;
import webserver.view.templater.IPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RootServlet extends HttpServlet {
    private IAccountService accountService;
    private ICookiesService cookiesService;
    private IPageGenerator pageGenerator;

    @Inject
    public RootServlet(IAccountService accountService, ICookiesService cookiesService, IPageGenerator pageGenerator) {
        this.accountService = accountService;
        this.cookiesService = cookiesService;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = cookiesService.getCookie(req, "id");
        if (idStr == null || accountService.getUsersCount() == 0) {
            resp.sendRedirect("login");
        } else {
            Long id = Long.parseLong(idStr);
            Map<String, Object> pageVariables = new HashMap<>();
            Follower follower = accountService.getFollower(id);
            Set<Following> following = follower.getFollowing();

            pageVariables.put("firstName", follower.getUserInfo().getFirstName());
            pageVariables.put("lastName", follower.getUserInfo().getLastName());
            pageVariables.put("following", following);
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println(pageGenerator.getPage("index.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
