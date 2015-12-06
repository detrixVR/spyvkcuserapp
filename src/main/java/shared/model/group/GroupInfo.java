package shared.model.group;

import javax.persistence.*;

@Entity
@Table(name = "groupinfo")
public class GroupInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vk_id")
    private Long vkId = 0L;

    @Column(name = "name")
    private String name = "";

    @Column(name = "screenname")
    private String screenName = "";

    public GroupInfo(Long vkId, String name, String screenName) {
        this.vkId = vkId;
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

    public Long getVkId() {
        return vkId;
    }

    public void setVkId(Long vkId) {
        this.vkId = vkId;
    }
}
