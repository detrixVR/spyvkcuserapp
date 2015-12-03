package shared.model.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groupinfo")
public class GroupInfo {
    @Id
    private Long id = 0L;

    @Column(name = "name")
    private String name = "";

    @Column(name = "screenname")
    private String screenName = "";

    public GroupInfo(Long id, String name, String screenName) {
        this.id = id;
        this.name = name;
        this.screenName = screenName;
    }

    public GroupInfo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public boolean equals(Object obj) {
        GroupInfo groupInfo = (GroupInfo) obj;
        return this.id == groupInfo.id && this.name.equals(groupInfo.name) && this.screenName.equals(groupInfo.screenName);
    }
}
