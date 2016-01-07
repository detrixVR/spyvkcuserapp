package shared.model.snapshots;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table
public class GroupListSnapshot extends Snapshot {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupSnapshot> groupSnapshots = new ArrayList<>();

    public List<GroupSnapshot> getGroupSnapshots() {
        return groupSnapshots;
    }

    public void setGroupSnapshots(List<GroupSnapshot> groupSnapshots) {
        this.groupSnapshots = groupSnapshots;
    }
}
