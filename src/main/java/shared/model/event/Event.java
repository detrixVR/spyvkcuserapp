package shared.model.event;

public abstract class Event {
    private TypeOfEvent typeOfEvent;

    public TypeOfEvent getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(TypeOfEvent typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }
}
