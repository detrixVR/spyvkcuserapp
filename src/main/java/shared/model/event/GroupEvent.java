package shared.model.event;

import shared.model.group.Group;

import javax.persistence.*;

@Entity
@Table
public class GroupEvent extends Event {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Group group;

    public GroupEvent(EventAction eventAction) {
        this.eventType = EventType.GROUP;
        this.eventAction = eventAction;
    }

    public GroupEvent() {}

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        EventAction eventAction = this.eventAction;
        if(eventAction == EventAction.ADD) stringBuilder.append("Добавил ");
        if(eventAction == EventAction.REMOVE) stringBuilder.append("Удалил ");
        stringBuilder.append("группу ");
        stringBuilder.append(group.getName());
        stringBuilder.append(".");
        return stringBuilder.toString();
    }
}
