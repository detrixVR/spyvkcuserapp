package controller;

import model.Client;
import view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        String userlink = requestUserlink();
        long userID = vkApi.resolveScreenName(userlink);
        UI.pleaseWait();
        List<Long> groupIDs = vkApi.getGroupIDs(userID);

        HashMap<Long, ArrayList<Long>> likedPostsIDsByGroups = new HashMap<>();

        for(Long groupID: groupIDs) {
            List<Long> postIDs = vkApi.getPostIDs(groupID);
            ArrayList<Long> likedPostIDs = new ArrayList<>();
            for (Long postID : postIDs) {
                try {
                    List<Long> userIDs = vkApi.getLikesUserIDs(groupID, postID);
                    if(checkPresenceOfUser(userIDs, userID, groupID, postID)) {
                        likedPostIDs.add(postID);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            likedPostsIDsByGroups.put(groupID, likedPostIDs);
        }
        UI.showLikedPosts(likedPostsIDsByGroups);
    }

    private String requestUserlink() throws IOException {
        System.out.print("User link: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private boolean checkPresenceOfUser(List<Long> userIDs, long userID, Long groupID, Long postID) {
        if(userIDs.contains(Long.valueOf(userID))) {
            return true;
        }
        return false;
    }
}
