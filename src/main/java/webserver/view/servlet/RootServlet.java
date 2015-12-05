package webserver.view.servlet;

import com.google.inject.Inject;
import shared.controller.account_service.IAccountService;
import shared.model.user.Following;
import webserver.controller.cookies_service.ICookiesService;
import shared.model.user.Follower;
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
    IAccountService accountService;
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
        if(accountService.getUsersCount() == 0) {
            resp.sendRedirect("login");
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            Long id = Long.valueOf(cookiesService.getCookie(req, "id"));
            Follower user = accountService.getFollower(id);
            Set<Following> following = user.getFollowing();

            pageVariables.put("firstName", user.getUserInfo().getFirstName());
            pageVariables.put("lastName", user.getUserInfo().getLastName());
            pageVariables.put("following", following);
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println(pageGenerator.getPage("index.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
