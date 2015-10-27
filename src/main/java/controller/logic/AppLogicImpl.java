package controller.logic;

import model.group.Group;
import model.group.GroupInfo;
import model.post.Post;

import java.util.ArrayList;

public class AppLogicImpl implements IAppLogic {
    @Override
    public ArrayList<GroupInfo> formGroupsInfoFromSources(String[] ids, String[] names, String[] screenNames) {
        ArrayList<GroupInfo> groupsInfo = new ArrayList<>();
        for(int i=0; i<ids.length; i++) {
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setId(Long.valueOf(ids[i]));
            groupInfo.setName(names[i]);
            groupInfo.setScreenName(screenNames[i]);
            groupsInfo.add(groupInfo);
        }
        return groupsInfo;
    }

    @Override
    public ArrayList<Group> filterGroupsByFollowerLike(ArrayList<Group> groups, Long userId) {
        ArrayList<Group> groupsWithPostsLikedByFollower = new ArrayList<>();

        for (Group group : groups) {
            ArrayList<Post> posts = group.getPosts();
            ArrayList<Post> likedPosts = new ArrayList<>();
            boolean isAdded = false;
            for (Post post : posts) {
                ArrayList<Long> likedUserIds = post.getLikedUserIds();
                if(likedUserIds.contains(userId)) {
                    likedPosts.add(post);
                    isAdded = true;
                }
            }
            if(isAdded) {
                Group newGroup = new Group(group.getGroupInfo(), likedPosts);
                groupsWithPostsLikedByFollower.add(newGroup);
            }
        }
        return groupsWithPostsLikedByFollower;
    }
}