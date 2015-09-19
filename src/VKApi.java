import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class VKApi {
    private Client client = null;

    public class Auth {
        public void authorize(Client client) {
            String authorizeLink = "https://oauth.vk.com/authorize?" +
                    "client_id=" + client.getClientID() +
                    "&redirect_uri=" + "https://oauth.vk.com/blank.html" +
                    "&display=" + "page" +
                    "&scope=" + "groups" +
                    "&response_type=" + "token" +
                    "&v=" + 5.37;
            showPopup(authorizeLink);
        }

        private void showPopup(String authorizeLink) {
            try {
                Desktop.getDesktop().browse(new URI(authorizeLink));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void authorize(Client client) {
        this.client = client;
        (new Auth()).authorize(client);
    }

    public long resolve(String userlink) {
        userlink = userlink.replace("https://vk.com/", "");
        try {
            String result = (new Request()).send("https://api.vk.com/method/utils.resolveScreenName?" +
                            "screen_name=" + userlink +
                            "&v=" + 5.37
            );
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getJSONObject("response").getLong("object_id");
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Long> getGroupIDs(long userID) {
        String groupsJSON = "";
        try {
            groupsJSON = (new Request()).send("https://api.vk.com/method/groups.get?" +
                            "user_id=" + userID +
                            "&count=" + 1000 +
                            "&v=" + 5.37 +
                            "&access_token=" + client.getAccessToken()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(groupsJSON);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
        ArrayList<Long> groupIDs = new ArrayList<>();
        for(int i=0; !jsonArray.isNull(i); i++) {
            groupIDs.add(jsonArray.getLong(i));
        }
        return groupIDs;
    }

    public ArrayList<Long> getPostIDs(long groupID) {
        String postsJSON = "";
        try {
            postsJSON = (new Request()).send("https://api.vk.com/method/wall.get?" +
                            "owner_id=-" + groupID +
                            "&offset=" + 0 +
                            "&count=" + 100 +
                            "&v=" + 5.37
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Long> postIDs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(postsJSON);
        JSONArray itemsArray = jsonObject.getJSONObject("response").getJSONArray("items");
        for (int i = 0; !itemsArray.isNull(i); i++) {
            postIDs.add(itemsArray.getJSONObject(i).getLong("id"));
        }
        return postIDs;
    }

    public ArrayList<Long> getLikesUserIDs(long groupID, long postID) throws IOException {
        String likesJSON = (new Request()).send("https://api.vk.com/method/likes.getList?" +
                        "type=" + "post" +
                        "&owner_id=-" + groupID +
                        "&item_id=" + postID +
                        "&offset=" + 0 +
                        "&count=" + 1000
        );
        ArrayList<Long> userIDs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(likesJSON);
        JSONArray userArray = jsonObject.getJSONObject("response").getJSONArray("users");
        for (int i = 0; !userArray.isNull(i); i++) {
            userIDs.add(userArray.getLong(i));
        }
        return userIDs;
    }
}
