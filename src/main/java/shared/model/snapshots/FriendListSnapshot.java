package shared.model.snapshots;

import shared.model.friend.Friend;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class FriendListSnapshot extends Snapshot<Friend> {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Friend> friendList = new ArrayList<>();

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    @Override
    public List<Friend> getList() {
        return friendList;
    }
}
