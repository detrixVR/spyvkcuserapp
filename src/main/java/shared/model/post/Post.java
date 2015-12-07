package shared.model.post;

import shared.model.group.Group;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vk_id")
    private Long vkId = 0L;

    @Column(name = "text", length = 10000)
    private String text = "";

    @Column(name = "date")
    private Long date = 0L;

    @Column(name = "likes_count")
    private Integer likesCount = 0;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Long.class)
    private Set<Long> likedUserIds = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(Set<Long> likedUserIds) {
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

    public Long getVkId() {
        return vkId;
    }

    public void setVkId(Long vkId) {
        this.vkId = vkId;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
