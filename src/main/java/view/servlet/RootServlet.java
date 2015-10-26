package view.servlet;

import controller.account_service.AccountService;
import controller.cookies_service.CookiesService;
import model.user.User;
import view.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RootServlet extends HttpServlet {
    AccountService accountService;
    private CookiesService cookiesService;
    private PageGenerator pageGenerator;

    public RootServlet(AccountService accountService, CookiesService cookiesService, PageGenerator pageGenerator) {
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
            User user = accountService.getUser(id);
            pageVariables.put("firstName", user.getUserInfo().getFirstName());
            pageVariables.put("lastName", user.getUserInfo().getLastName());
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println(pageGenerator.getPage("index.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
