package controller;

import model.Client;
import view.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VKChase {
    private Client client;
    private VKApi vkApi;

    public VKChase() {
        vkApi = new VKApi();
        client = new Client(5071208);
    }

    public void collectInfo() throws IOException {
        String accessToken = vkApi.authorize(client);
        client.setAccessToken(accessToken);
        String userlink = UI.getInstance().requestUserlink();
        long userID = vkApi.resolveScreenName(userlink);
        List<Long> groupIDs = vkApi.getGroupIDs(userID);
        HashMap<Long, ArrayList<Long>> likedPostsIDsByGroups = findLikedPostsIDsByGroups(groupIDs, userID);
        UI.getInstance().showLikedPosts(likedPostsIDsByGroups);
    }

    private HashMap<Long, ArrayList<Long>> findLikedPostsIDsByGroups(List<Long> groupIDs, long userID) throws IOException {
        HashMap<Long, ArrayList<Long>> likedPostsIDsByGroups = new HashMap<>();
        int countOfPosts = UI.getInstance().requestCountOfPosts();
        UI.getInstance().pleaseWait();
        for(Long groupID: groupIDs) {
            List<Long> postIDs = vkApi.getPostIDs(groupID, countOfPosts);
            ArrayList<Long> likedPostIDs = new ArrayList<>();
            for (Long postID : postIDs) {
                try {
                    List<Long> userIDs = vkApi.getLikesUserIDs(groupID, postID);
                    if(checkPresenceOfUser(userIDs, userID)) {
                        likedPostIDs.add(postID);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            likedPostsIDsByGroups.put(groupID, likedPostIDs);
        }
        return likedPostsIDsByGroups;
    }


    private boolean checkPresenceOfUser(List<Long> userIDs, long userID) {
       return userIDs.contains(Long.valueOf(userID));
    }
}
