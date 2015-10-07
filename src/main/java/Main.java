import controller.VKApi;
import controller.VKApiImpl;
import controller.VKChase;
import model.Client;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import view.RootServlet;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Servlet rootServlet = new RootServlet();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(rootServlet), "/");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
//        VKApi vkApi = new VKApiImpl();
//        Client client = new Client(5071208);
//        VKChase vkChase = new VKChase(vkApi, client);
//        HashMap<Long, ArrayList<Long>> likedPostsIDsByGroups = vkChase.collectInfo();
//        UI.getInstance().showLikedPosts(likedPostsIDsByGroups);
    }
}
