package shared.controller.json_service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import shared.model.audio.Audio;
import shared.model.friend.Friend;
import shared.model.group.Group;
import shared.model.post.Post;
import shared.model.user.UserInfo;
import shared.model.video.Video;

import java.sql.Timestamp;
import java.util.*;

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
        userInfo.setVkId(userObject.getLong("id"));
        userInfo.setFirstName(userObject.getString("first_name"));
        userInfo.setLastName(userObject.getString("last_name"));
        return userInfo;
    }

    @Override
    public Long getFollowingId(String answer) {
        JSONObject responseObject = new JSONObject(answer);
        return responseObject.getJSONObject("response").getLong("object_id");
    }

    @Override
    public ArrayList<Long> getGroupsIds(String answer) {
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
        ArrayList<Long> groupIDs = new ArrayList<>();
        for (int i = 0; !jsonArray.isNull(i); i++) {
            groupIDs.add(jsonArray.getLong(i));
        }
        return groupIDs;
    }

    @Override
    public ArrayList<Group> getGroups(String answer) {
        ArrayList<Group> groups = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(answer);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        for (int i = 0; !jsonArray.isNull(i); i++) {
            JSONObject groupObject = jsonArray.getJSONObject(i);
            Group group = new Group();
            group.setVkId(groupObject.getLong("id"));
            group.setName(groupObject.getString("name"));
            group.setScreenName(groupObject.getString("screen_name"));
            groups.add(group);
        }
        return groups;
    }

    @Override
    public Set<Post> getPosts(String answer) {
        Set<Post> posts = new LinkedHashSet<>();
        try {
            JSONObject jsonObject = new JSONObject(answer);
            System.out.println(answer);
            JSONArray itemsArray = jsonObject.getJSONObject("response").getJSONArray("items");
            for (int i = 0; !itemsArray.isNull(i); i++) {
                Post post = new Post();
                JSONObject item = itemsArray.getJSONObject(i);
                post.setVkId(item.getLong("id"));
                post.setText(item.getString("text"));
                post.setDate(item.getLong("date"));
                JSONObject likes = item.getJSONObject("likes");
                post.setLikesCount(likes.getInt("count"));
                posts.add(post);
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            System.out.println("Too many requests per second");
            return null;
        }
        return posts;
    }

    @Override
    public Set<Long> getLikedUserIds(String answer) {
        Set<Long> userIDs = new HashSet<>();
        try {
            JSONObject jsonObject = new JSONObject(answer);
            System.out.println(answer);
            JSONArray userArray = jsonObject.getJSONObject("response").getJSONArray("items");
            for (int i = 0; !userArray.isNull(i); i++) {
                userIDs.add(userArray.getLong(i));
            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Too many requests per second");
            return null;
        }
        return userIDs;
    }

    @Override
    public String getClientSecret(String valuesJson) {
        JSONObject jsonObject = new JSONObject(valuesJson);
        return jsonObject.getString("client_secret");
    }

    @Override
    public List<Audio> getAudio(String answer) {
        List<Audio> audioList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(answer);
            JSONArray audioArray = jsonObject.getJSONObject("response").getJSONArray("items");
            for (int i = 0; !audioArray.isNull(i); i++) {
                Audio audio = new Audio();
                JSONObject audioObject = audioArray.getJSONObject(i);
                audio.setVkId(audioObject.getLong("id"));
                audio.setArtist(audioObject.getString("artist"));
                audio.setTitle(audioObject.getString("title"));
                audio.setUrl(audioObject.getString("url"));
                audioList.add(audio);
            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Too many requests per second");
            return null;
        }
        return audioList;
    }

    @Override
    public List<Video> getVideo(String answer) {
        List<Video> videoList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(answer);
            JSONArray videoArray = jsonObject.getJSONObject("response").getJSONArray("items");
            for (int i = 0; !videoArray.isNull(i); i++) {
                Video video = new Video();
                JSONObject audioObject = videoArray.getJSONObject(i);
                video.setVkId(audioObject.getLong("id"));
                video.setTitle(audioObject.getString("title"));
                videoList.add(video);
            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Too many requests per second");
            return null;
        }
        return videoList;
    }

    @Override
    public List<Friend> getFriends(String answer) {
        List<Friend> friendsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(answer);
            JSONArray friendArray = jsonObject.getJSONObject("response").getJSONArray("items");
            for (int i = 0; !friendArray.isNull(i); i++) {
                Friend friend = new Friend();
                JSONObject friendObject = friendArray.getJSONObject(i);
                friend.setVkId(friendObject.getLong("id"));
                friend.setFirstName(friendObject.getString("first_name"));
                friend.setLastName(friendObject.getString("last_name"));
                friendsList.add(friend);
            }
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Too many requests per second");
            return null;
        }
        return friendsList;
    }
}
