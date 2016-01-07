package shared.model.snapshots;

import shared.model.group.Group;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class GroupListSnapshot extends Snapshot<Group> {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Group> groupList = new ArrayList<>();

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    @Override
    public List<Group> getList(){
        return groupList;
    }
}
