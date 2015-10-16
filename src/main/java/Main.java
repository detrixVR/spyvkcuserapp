import controller.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import view.*;

import javax.servlet.Servlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Request request = new Request();
        LinkBuilder linkBuilder = new LinkBuilder();
        AccountService accountService = new AccountService();
        JsonService jsonService = new JsonService();
        CookiesService cookiesService = new CookiesService();
        ApiService apiService = new ApiService(request, linkBuilder, jsonService);
        Logic logic = new Logic();

        Servlet rootServlet = new RootServlet(accountService, cookiesService);
        Servlet loginServlet = new LoginServlet(apiService, accountService);
        Servlet followerServlet = new AddFollowerServlet(apiService, accountService, cookiesService);
        Servlet getGroupsServlet = new GetGroupsServlet(apiService, accountService, cookiesService);
        Servlet likesInGroupsServlet = new LikesInGroupsServlet(apiService, accountService, cookiesService, logic);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/vkchase");

        context.addServlet(new ServletHolder(rootServlet), "/");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(followerServlet), "/follower");
        context.addServlet(new ServletHolder(getGroupsServlet), "/groups");
        context.addServlet(new ServletHolder(likesInGroupsServlet), "/grouplikes");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
