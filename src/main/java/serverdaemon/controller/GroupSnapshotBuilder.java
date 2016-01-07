package serverdaemon.controller;

import shared.model.group.Group;
import shared.model.snapshots.GroupSnapshot;
import shared.model.post.Post;
import shared.model.snapshots.PostSnapshot;

import java.util.Set;

public class GroupSnapshotBuilder implements SnapshotBuilder<GroupSnapshot, Group> {
    @Override
    public GroupSnapshot build(Group group) {
        GroupSnapshot groupSnapshot = new GroupSnapshot();
        long dateOfSnapshot = System.currentTimeMillis()/1000;
        Set<Post> posts = group.getPosts();
        for (Post post : posts) {
            PostSnapshot postSnapshot = new PostSnapshot();
//            postSnapshot.setVkId(post.getVkId());
//            postSnapshot.setText(post.getText());
//            postSnapshot.setDate(post.getDate());
//            postSnapshot.getLikedUserIds().addAll(post.getLikedUserIds());
            postSnapshot.setPost(post);
            postSnapshot.setSnapshotDate(dateOfSnapshot);
            postSnapshot.setGroupSnapshot(groupSnapshot);
            groupSnapshot.getPostSnapshots().add(postSnapshot);
        }
        groupSnapshot.setSnapshotDate(dateOfSnapshot);
        groupSnapshot.setGroup(group);
        return groupSnapshot;
    }
}
