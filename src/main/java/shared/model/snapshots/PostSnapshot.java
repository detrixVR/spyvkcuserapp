package shared.model.snapshots;

import shared.model.post.Post;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "postsnapshot")
public class PostSnapshot extends Snapshot {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_snapshot")
    private GroupSnapshot groupSnapshot;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Post post;

//    @Column(name = "text", length = 10000)
//    private String text;
//
//    @Column(name = "vk_id")
//    private long vkId;
//
//    @ElementCollection(fetch = FetchType.LAZY, targetClass = Long.class)
//    private Set<Long> likedUserIds = new HashSet<>();

    @Column(name = "date")
    private Long snapshotDate;

    public GroupSnapshot getGroupSnapshot() {
        return groupSnapshot;
    }

    public void setGroupSnapshot(GroupSnapshot groupSnapshot) {
        this.groupSnapshot = groupSnapshot;
    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public long getVkId() {
//        return vkId;
//    }
//
//    public void setVkId(long vkId) {
//        this.vkId = vkId;
//    }
//
//    public Set<Long> getLikedUserIds() {
//        return likedUserIds;
//    }
//
//    public void setLikedUserIds(Set<Long> likedUserIds) {
//        this.likedUserIds = likedUserIds;
//    }

    public Long getDate() {
        return snapshotDate;
    }

    public void setDate(Long date) {
        this.snapshotDate = date;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
