package shared.model.event;

import shared.model.post.Post;

import javax.persistence.*;

@Entity
@Table
public class PostEvent extends Event {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Post post;

    public PostEvent(EventAction eventAction) {
        this.eventType = EventType.POST;
        this.eventAction = eventAction;
    }

    public PostEvent() {}

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        EventAction eventAction = this.eventAction;
        if(eventAction == EventAction.ADD) stringBuilder.append("Добавил ");
        if(eventAction == EventAction.REMOVE) stringBuilder.append("Удалил ");
        stringBuilder.append("пост ");
        stringBuilder.append(post.getText());
        stringBuilder.append(".");
        return stringBuilder.toString();
    }
}
