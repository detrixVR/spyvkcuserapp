package websever.view.servlet;

import com.google.inject.Inject;
import serverdaemon.controller.account_service.IAccountService;
import serverdaemon.controller.api_service.IApiService;
import serverdaemon.controller.cookies_service.ICookiesService;
import shared.model.user.Follower;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddFollowingServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;
    private ICookiesService cookiesService;

    @Inject
    public AddFollowingServlet(IApiService apiService, IAccountService accountService, ICookiesService cookiesService) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.cookiesService = cookiesService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLink = req.getParameter("link");
        Long id = Long.valueOf(cookiesService.getCookie(req, "id"));
        Follower user = accountService.getUser(id);
        Long followerId = apiService.resolveScreenName(userLink);
//        user.addFollowerId(followerId);
        resp.sendRedirect("groups?follower=" + followerId);
    }
}
