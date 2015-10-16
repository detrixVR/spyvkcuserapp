package controller;

import model.Group;
import model.Post;
import model.UserInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonService {
    public String getAccessToken(String answer) {
        JSONObject jsonObject = new JSONObject(answer);
        return jsonObject.getString("access_token");
    }

    public UserInfo getUserInfo(String userInfoString) {
        JSONObject responseObject = new JSONObject(userInfoString);
        JSONObject userObject = responseObject.getJSONArray("response").getJSONObject(0);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userObject.getInt("id"));
        userInfo.setFirstName(userObject.getString("first_name"));
        userInfo.setLastName(userObject.getString("last_name"));
        return userInfo;
    }

    public Long getFollowerId(String answer) {
        JSONObject responseObject = new JSONObject(answer);
        return responseObject.getJSONObject("response").getLong("object_id");
    }

    public ArrayList<Long> getGroupsIds(String answer) {
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
        ArrayList<Long> groupIDs = new ArrayList<>();
        for(int i=0; !jsonArray.isNull(i); i++) {
            groupIDs.add(jsonArray.getLong(i));
        }
        return groupIDs;
    }

    public ArrayList<Group> getGroups(String answer) {
        ArrayList<Group> groups = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        for(int i=0; !jsonArray.isNull(i); i++) {
            JSONObject groupObject = jsonArray.getJSONObject(i);
            Group group = new Group();
            group.setId(groupObject.getLong("id"));
            group.setName(groupObject.getString("name"));
            group.setScreenName(groupObject.getString("screen_name"));
            groups.add(group);
        }
        return groups;
    }

    public ArrayList<Post> getPosts(String answer) {
        ArrayList<Post> posts = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray itemsArray = jsonObject.getJSONObject("response").getJSONArray("items");
        for (int i = 0; !itemsArray.isNull(i); i++) {
            Post post = new Post();
            JSONObject item = itemsArray.getJSONObject(i);
            post.setId(item.getLong("id"));
            post.setText(item.getString("text"));
            posts.add(post);
        }
        return posts;
    }

    public ArrayList<Long> getLikedUserIds(String answer) {
        ArrayList<Long> userIDs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray userArray = jsonObject.getJSONObject("response").getJSONArray("users");
        for (int i = 0; !userArray.isNull(i); i++) {
            userIDs.add(userArray.getLong(i));
        }
        return userIDs;
    }

    public String getClientSecret(String valuesJson) {
        JSONObject jsonObject = new JSONObject(valuesJson);
        return jsonObject.getString("client_secret");
    }
}
