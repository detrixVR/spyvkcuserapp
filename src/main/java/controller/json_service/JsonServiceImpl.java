package controller.json_service;

import model.group.GroupInfo;
import model.post.Post;
import model.user.UserInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonServiceImpl implements IJsonService {
    @Override
    public String getAccessToken(String answer) {
        JSONObject jsonObject = new JSONObject(answer);
        return jsonObject.getString("access_token");
    }

    @Override
    public UserInfo getUserInfo(String userInfoString) {
        JSONObject responseObject = new JSONObject(userInfoString);
        JSONObject userObject = responseObject.getJSONArray("response").getJSONObject(0);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userObject.getInt("id"));
        userInfo.setFirstName(userObject.getString("first_name"));
        userInfo.setLastName(userObject.getString("last_name"));
        return userInfo;
    }

    @Override
    public Long getFollowerId(String answer) {
        JSONObject responseObject = new JSONObject(answer);
        return responseObject.getJSONObject("response").getLong("object_id");
    }

    @Override
    public ArrayList<Long> getGroupsIds(String answer) {
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
        ArrayList<Long> groupIDs = new ArrayList<>();
        for(int i=0; !jsonArray.isNull(i); i++) {
            groupIDs.add(jsonArray.getLong(i));
        }
        return groupIDs;
    }

    @Override
    public ArrayList<GroupInfo> getGroupsInfo(String answer) {
        ArrayList<GroupInfo> groupsInfo = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        for(int i=0; !jsonArray.isNull(i); i++) {
            JSONObject groupObject = jsonArray.getJSONObject(i);
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setId(groupObject.getLong("id"));
            groupInfo.setName(groupObject.getString("name"));
            groupInfo.setScreenName(groupObject.getString("screen_name"));
            groupsInfo.add(groupInfo);
        }
        return groupsInfo;
    }

    @Override
    public ArrayList<Post> getPosts(String answer) {
        ArrayList<Post> posts = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(answer);
        System.out.println(answer);
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

    @Override
    public ArrayList<Long> getLikedUserIds(String answer) {
        ArrayList<Long> userIDs = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(answer);
            System.out.println(answer);
            JSONArray userArray = jsonObject.getJSONObject("response").getJSONArray("items");
            for (int i = 0; !userArray.isNull(i); i++) {
                userIDs.add(userArray.getLong(i));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            return userIDs;
        }
        return userIDs;
    }

    @Override
    public String getClientSecret(String valuesJson) {
        JSONObject jsonObject = new JSONObject(valuesJson);
        return jsonObject.getString("client_secret");
    }
}
