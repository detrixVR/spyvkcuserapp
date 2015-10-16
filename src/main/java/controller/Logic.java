package controller;

import model.Group;
import model.Post;

import java.util.ArrayList;

public class Logic {
    public ArrayList<Group> formGroupsFromSources(String[] ids, String[] names, String[] screenNames) {
        ArrayList<Group> groups = new ArrayList<>();
        for(int i=0; i<ids.length; i++) {
            Group group = new Group();
            group.setId(Long.valueOf(ids[i]));
            group.setName(names[i]);
            group.setScreenName(screenNames[i]);
            groups.add(group);
        }
        return groups;
    }

    public ArrayList<Group> findGroupsWithPostsLikedByUser(ArrayList<Group> groups, Long userId) {
        ArrayList<Group> groupsWithPostsLikedByUser = new ArrayList<>();
        for (Group group : groups) {
            ArrayList<Post> posts = group.getPosts();
            Group newGroup = new Group();
            newGroup.setId(group.getId());
            newGroup.setName(group.getName());
            newGroup.setScreenName(group.getScreenName());
            boolean isAdded = false;
            for (Post post : posts) {
                ArrayList<Long> likedUserIds = post.getLikedUserIds();
                if(likedUserIds.contains(userId)) {
                    newGroup.getPosts().add(post);
                    isAdded = true;
                }
            }
            if(isAdded) {
                groupsWithPostsLikedByUser.add(newGroup);
            }
        }
        return groupsWithPostsLikedByUser;
    }
}
