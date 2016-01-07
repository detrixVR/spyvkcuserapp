package shared.model.snapshots;

import shared.model.group.Group;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table
public class GroupListSnapshot extends Snapshot<GroupSnapshot> {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupSnapshot> groupSnapshots = new ArrayList<>();

    public List<GroupSnapshot> getGroupSnapshots() {
        return groupSnapshots;
    }

    public void setGroupSnapshots(List<GroupSnapshot> groupSnapshots) {
        this.groupSnapshots = groupSnapshots;
    }

    @Override
    public List<GroupSnapshot> getList() {
        return groupSnapshots;
    }
}
