package shared.model.snapshots;

import shared.model.group.Group;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class GroupSnapshot extends Snapshot {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private Group group;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "groupSnapshot")
    @Column(name = "post_snapshots")
    private Set<PostSnapshot> postSnapshots = new HashSet<>();

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<PostSnapshot> getPostSnapshots() {
        return postSnapshots;
    }
}
