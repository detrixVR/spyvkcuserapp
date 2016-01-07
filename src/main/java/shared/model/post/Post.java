package shared.model.post;

import shared.model.group.Group;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vkId = 0L;

    private Long ownerId = 0L;

    @Column(length = 10000)
    private String text = "";

    private Long date = 0L;

    private Integer likesCount = 0;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Long.class)
    private Set<Long> likedUserIds = new HashSet<>();

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
        return post.getVkId().equals(this.getVkId());
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
