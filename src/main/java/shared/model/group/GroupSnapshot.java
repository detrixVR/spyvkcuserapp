package shared.model.group;

import shared.model.post.PostSnapshot;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groupsnaphot")
public class GroupSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "`group`")
    private Group group;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "groupSnapshot")
    @Column(name = "post_snapshots")
    private Set<PostSnapshot> postSnapshots = new HashSet<>();

    @Column(name = "date_of_snapshot")
    private Long dateOfSnapshot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<PostSnapshot> getPostSnapshots() {
        return postSnapshots;
    }

    public Long getDateOfSnapshot() {
        return dateOfSnapshot;
    }

    public void setDateOfSnapshot(Long dateOfSnapshot) {
        this.dateOfSnapshot = dateOfSnapshot;
    }
}
