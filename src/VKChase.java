import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class VKChase {
    public static void main(String[] args) throws IOException, URISyntaxException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String authorizeLink = "https://oauth.vk.com/authorize?client_id=5071208&redirect_uri=https://oauth.vk.com/blank.html&display=page&scope=groups&response_type=token&v=5.37";
        if(Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(authorizeLink));
        }
        System.out.print("Access token: ");
        String accessToken = br.readLine();

        System.out.print("User link: ");
        String userlink = br.readLine();
        br.close();
        String result = Request.send("https://api.vk.com/method/utils.resolveScreenName?screen_name=" + userlink + "&v=5.37");
        System.out.println(result);
        JSONObject jsonObject;
        jsonObject = new JSONObject(result);
        long userID = jsonObject.getJSONObject("response").getLong("object_id");
        System.out.println(userID);

        String groupsJSON = Request.send("https://api.vk.com/method/groups.get?user_id=" + userID + "&count=1000&v=5.37&access_token=" + accessToken);
        jsonObject = new JSONObject(groupsJSON);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
        List<Long> groups = new ArrayList<>();
        for(int i=0; !jsonArray.isNull(i); i++) {
            groups.add(jsonArray.getLong(i));
        }

        for(Long group: groups) {
            System.out.println("Group " + group + ": ");
            String postsJSON = Request.send("https://api.vk.com/method/wall.get?owner_id=-" + group + "&offset=0&count=100&v=5.37");
            List<Long> postIDs = new ArrayList<>();
            jsonObject = new JSONObject(postsJSON);
            try {
                JSONArray itemsArray = jsonObject.getJSONObject("response").getJSONArray("items");
                for (int i = 0; !itemsArray.isNull(i); i++) {
                    postIDs.add(itemsArray.getJSONObject(i).getLong("id"));
                }

                for (Long postID : postIDs) {
                    String likesJSON = Request.send("https://api.vk.com/method/likes.getList?type=post&owner_id=-" + group + "&item_id=" + postID + "&offset=0&count=1000");
                    List<Long> users = new ArrayList<>();
                    jsonObject = new JSONObject(likesJSON);
                    JSONArray userArray = jsonObject.getJSONObject("response").getJSONArray("users");
                    for (int i = 0; !userArray.isNull(i); i++) {
                        users.add(userArray.getLong(i));
                    }
                    if (users.contains(Long.valueOf(userID)))
                        System.out.println("https://vk.com/wall-" + group + "_" + postID);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
