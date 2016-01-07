package serverdaemon.controller.logic;

import shared.model.group.Group;
import shared.model.post.Post;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class AppLogicImpl implements IAppLogic {
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
                Group newGroup = new Group(likedPosts);
                groupsWithPostsLikedByFollowing.add(newGroup);
            }
        }
        return groupsWithPostsLikedByFollowing;
    }
}
