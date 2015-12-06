package webserver.view.servlet;

import com.google.inject.Inject;
import shared.controller.account_service.IAccountService;
import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.user.Follower;
import shared.model.user.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;
    private IDBService dbService;

    @Inject
    public LoginServlet(IApiService apiService, IAccountService accountService, IDBService dbService) {
        this.apiService = apiService;
        this.accountService = accountService;
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("code") != null) {
            String code = req.getParameter("code");
            String accessToken = apiService.requestAccessToken(code);
            UserInfo userInfo = apiService.getUserInfo(null, accessToken);
            Follower follower = new Follower(userInfo, accessToken);

            accountService.saveFollower(userInfo.getVkId(), follower);
            dbService.saveFollower(follower);

            resp.addCookie(new Cookie("id", String.valueOf(userInfo.getVkId())));
            resp.sendRedirect("");
        } else {
            String requestCodeLink = apiService.getRequestCodeLink();
            resp.sendRedirect(requestCodeLink);
        }
    }
}
