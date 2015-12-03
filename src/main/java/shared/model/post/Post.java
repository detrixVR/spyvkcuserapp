package shared.model.post;

import shared.model.group.Group;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    private Long id = 0L;

    @Column(name = "text")
    private String text = "";

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Long.class)
    private List<Long> likedUserIds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(List<Long> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
       this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        Post post = (Post) obj;
        return this.id.equals(post.id) && this.text.equals(post.text) && this.likedUserIds.equals(post.likedUserIds);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
