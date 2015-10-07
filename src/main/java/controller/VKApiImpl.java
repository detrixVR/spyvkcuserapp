package controller;

import model.Client;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class VKApiImpl implements VKApi {
    private Client client = null;

    public String authorize(Client client) {
        this.client = client;
        return null;
    }

    public long resolveScreenName(String userlink) {
        userlink = userlink.replace("https://vk.com/", "");
        try {
            StringBuilder request = new StringBuilder();
            request.append("https://api.vk.com/method/utils.resolveScreenName?");
            request.append("screen_name=");
            request.append(userlink);
            request.append("&v=5.37");

            String result = (new Request()).get(request.toString());
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
            StringBuilder request = new StringBuilder();
            request.append("https://api.vk.com/method/groups.get?");
            request.append("user_id=");
            request.append(userID);
            request.append("&count=1000");
            request.append("&v=5.37");
            request.append("&access_token=");
            request.append(client.getAccessToken());

            groupsJSON = (new Request()).get(request.toString());
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

    public ArrayList<Long> getPostIDs(long groupID, int countOfPosts) {
        String postsJSON = "";
        try {
            StringBuilder request = new StringBuilder();
            request.append("https://api.vk.com/method/wall.get?");
            request.append("owner_id=-");
            request.append(groupID);
            request.append("&offset=0");
            request.append("&count=");
            request.append(countOfPosts);
            request.append("&v=5.37");

            postsJSON = (new Request()).get(request.toString());
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
        StringBuilder request = new StringBuilder();
        request.append("https://api.vk.com/method/likes.getList?");
        request.append("type=post");
        request.append("&owner_id=-");
        request.append(groupID);
        request.append("&item_id=");
        request.append(postID);
        request.append("&offset=0");
        request.append("&count=1000");

        String likesJSON = (new Request()).get(request.toString());
        ArrayList<Long> userIDs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(likesJSON);
        JSONArray userArray = jsonObject.getJSONObject("response").getJSONArray("users");
        for (int i = 0; !userArray.isNull(i); i++) {
            userIDs.add(userArray.getLong(i));
        }
        return userIDs;
    }
}
