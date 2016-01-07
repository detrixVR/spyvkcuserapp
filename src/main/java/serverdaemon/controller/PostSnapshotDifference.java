package serverdaemon.controller;

import shared.model.event.Event;
import shared.model.event.PostEvent;
import shared.model.post.Post;
import shared.model.snapshots.PostListSnapshot;

import java.util.List;

public class PostSnapshotDifference extends SnapshotDifference<PostListSnapshot, Post, PostEvent> {
    @Override
    protected void setAction(PostEvent concreteEvent, Post action) {
        concreteEvent.setPost(action);
    }

    @Override
    protected Post getConcreteEventEntity(PostEvent concreteEvent) {
        return concreteEvent.getPost();
    }

    @Override
    protected List<Post> getListOfEventEntity(PostListSnapshot postListSnapshot) {
        return postListSnapshot.getList();
    }

    @Override
    protected PostEvent createTypeOfEvent(Event.EventAction eventAction) {
        return new PostEvent(eventAction);
    }
}
