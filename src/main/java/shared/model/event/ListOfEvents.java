package shared.model.event;

import java.util.ArrayList;
import java.util.List;

public class ListOfEvents {
    private List<TypeOfEvent> typesOfEvents = new ArrayList<>();

    public List<TypeOfEvent> getTypesOfEvents() {
        return typesOfEvents;
    }

    public void setTypesOfEvents(List<TypeOfEvent> typesOfEvents) {
        this.typesOfEvents = typesOfEvents;
    }

    public void add(TypeOfEvent t) {
        this.typesOfEvents.add(t);
    }
}
