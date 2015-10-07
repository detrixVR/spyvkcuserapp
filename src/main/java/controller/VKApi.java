package controller;

import com.sun.istack.internal.NotNull;
import model.Client;

import java.io.IOException;
import java.util.ArrayList;

public interface VKApi {
    String authorize(@NotNull Client client);
    long resolveScreenName(String userlink);
    ArrayList<Long> getGroupIDs(long userID);
    ArrayList<Long> getPostIDs(long groupID, int countOfPosts);
    ArrayList<Long> getLikesUserIDs(long groupID, long postID) throws IOException;
}
