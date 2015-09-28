import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        long userID = vkApi.resolve(userlink);
        List<Long> groupIDs = vkApi.getGroupIDs(userID);

        for(Long groupID: groupIDs) {
            System.out.println("Group " + groupID + ": ");
            List<Long> postIDs = vkApi.getPostIDs(groupID);
            for (Long postID : postIDs) {
                try {
                    List<Long> userIDs = vkApi.getLikesUserIDs(groupID, postID);
                    checkPresenceOfUser(userIDs, userID, groupID, postID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String requestUserlink() throws IOException {
        System.out.print("User link: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private void checkPresenceOfUser(List<Long> userIDs, long userID, Long groupID, Long postID) {
        if(userIDs.contains(Long.valueOf(userID))) {
            System.out.println("https://vk.com/wall-" + groupID + "_" + postID);
        }
    }
}
