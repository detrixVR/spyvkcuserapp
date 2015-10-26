package view.servlet;

import com.google.inject.Inject;
import controller.account_service.IAccountService;
import controller.api_service.IApiService;
import model.user.User;
import model.user.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private IApiService apiService;
    private IAccountService accountService;

    @Inject
    public LoginServlet(IApiService apiService, IAccountService accountService) {
        this.apiService = apiService;
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("code") != null) {
            String code = req.getParameter("code");
            String accessToken = apiService.requestAccessToken(code);
            UserInfo userInfo = apiService.getUserInfo(null, accessToken);
            User user = new User(userInfo, accessToken);
            accountService.addUser(userInfo.getId(), user);
            resp.addCookie(new Cookie("id", String.valueOf(userInfo.getId())));
            resp.sendRedirect("");
        } else {
            String requestCodeLink = apiService.getRequestCodeLink();
            resp.sendRedirect(requestCodeLink);
        }
    }
}
