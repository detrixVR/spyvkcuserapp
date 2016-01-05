package shared.model.post;

import shared.model.group.GroupSnapshot;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "postsnapshot")
public class PostSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_snapshot")
    private GroupSnapshot groupSnapshot;

    @Column(name = "text", length = 10000)
    private String text;

    @Column(name = "vk_id")
    private long vkId;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Long.class)
    private Set<Long> likedUserIds = new HashSet<>();

    @Column(name = "date")
    private Long date;

    @Column(name = "date_of_snapshot")
    private Long dateOfSnapshot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupSnapshot getGroupSnapshot() {
        return groupSnapshot;
    }

    public void setGroupSnapshot(GroupSnapshot groupSnapshot) {
        this.groupSnapshot = groupSnapshot;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getVkId() {
        return vkId;
    }

    public void setVkId(long vkId) {
        this.vkId = vkId;
    }

    public Set<Long> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(Set<Long> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDateOfSnapshot() {
        return dateOfSnapshot;
    }

    public void setDateOfSnapshot(Long dateOfSnapshot) {
        this.dateOfSnapshot = dateOfSnapshot;
    }
}
