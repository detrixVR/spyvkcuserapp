package view;

import controller.ApiService;
import controller.Logic;
import model.Group;
import model.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class LikesInGroupsServlet extends HttpServlet {
    private ApiService apiService;
    private Logic logic;

    public LikesInGroupsServlet(ApiService apiService, Logic logic) {
        this.apiService = apiService;
        this.logic = logic;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] groupIds = req.getParameterValues("id");
        String[] groupNames = req.getParameterValues("name");
        String[] groupScreenNames = req.getParameterValues("screenName");
        String[] count = req.getParameterValues("count");
        ArrayList<Group> groups = logic.formGroupsFromSources(groupIds, groupNames, groupScreenNames);

        for (int i=0; i<groups.size(); i++) {
            ArrayList<Post> posts = apiService.requestPosts(Long.valueOf(groupIds[i]), Integer.valueOf(count[i]));
            for (Post post : posts) {
                ArrayList<Long> userIds = apiService.requestLikedUserIds(Long.valueOf(groupIds[i]), post.getId());
                post.setLikedUserIds(userIds);
            }
            groups.get(i).setPosts(posts);
        }
    }
}
