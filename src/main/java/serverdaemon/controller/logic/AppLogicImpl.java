package serverdaemon.controller.logic;

import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.post.Post;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class AppLogicImpl implements IAppLogic {
    @Override
    public ArrayList<GroupInfo> formGroupsInfoFromSources(String[] ids, String[] names, String[] screenNames) {
        ArrayList<GroupInfo> groupsInfo = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setId(Long.valueOf(ids[i]));
            groupInfo.setName(names[i]);
            groupInfo.setScreenName(screenNames[i]);
            groupsInfo.add(groupInfo);
        }
        return groupsInfo;
    }

    @Override
    public Set<Group> filterGroupsByFollowingLike(Set<Group> groups, Long userId) {
        Set<Group> groupsWithPostsLikedByFollowing = new HashSet<>();

        for (Group group : groups) {
            Set<Post> posts = group.getPosts();
            Set<Post> likedPosts = new LinkedHashSet<>();
            boolean isAdded = false;
            for (Post post : posts) {
                Set<Long> likedUserIds = post.getLikedUserIds();
                if (likedUserIds.contains(userId)) {
                    likedPosts.add(post);
                    isAdded = true;
                }
            }
            if (isAdded) {
                Group newGroup = new Group(group.getGroupInfo(), likedPosts);
                groupsWithPostsLikedByFollowing.add(newGroup);
            }
        }
        return groupsWithPostsLikedByFollowing;
    }
}
