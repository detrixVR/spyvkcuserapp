package view.servlet;

import controller.account_service.AccountService;
import controller.api_service.ApiService;
import controller.cookies_service.CookiesService;
import model.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddFollowerServlet extends HttpServlet {
    private ApiService apiService;
    private AccountService accountService;
    private CookiesService cookiesService;

    public AddFollowerServlet(ApiService apiService, AccountService accountService, CookiesService cookiesService) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLink = req.getParameter("link");
        Long id = Long.valueOf(cookiesService.getCookie(req, "id"));
        User user = accountService.getUser(id);
        Long followerId = apiService.resolveScreenName(userLink);
        user.addFollowerId(followerId);
        resp.sendRedirect("groups?follower=" + followerId);
    }
}
