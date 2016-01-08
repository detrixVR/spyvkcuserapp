package handler.snapshot_difference;

import shared.model.event.Event;
import shared.model.event.EventType;
import shared.model.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SnapshotDifference<SnapshotCast extends Snapshot, EventEntity, TypeOfEvent extends Event> {
    @SuppressWarnings(value = "unchecked")
    public List<Event> difference(Snapshot oldS, Snapshot newS, List<Event> events, EventType eventType) {
        List<Event> newEvents = new ArrayList<>();
        List<Event> concreteEvents = getConcreteEvents(events, eventType); // get events of concrete type

        SnapshotCast one = (SnapshotCast) oldS;
        SnapshotCast two = (SnapshotCast) newS;
        List<EventEntity> oneList = new ArrayList<>(getListOfEventEntity(one)); // list of old event entities
        List<EventEntity> twoList = getListOfEventEntity(two); // list of new event entities

        addEventsToSnapshotList(concreteEvents, oneList);

        List<EventEntity> addList = getListOfAddedEventEntities(oneList, twoList);
        List<EventEntity> removeList = getListOfRemovedEventEntities(oneList, twoList);

        createNewAddEvents(newEvents, addList);
        createNewRemoveEvents(newEvents, removeList);

        return newEvents;
    }

    private void createNewRemoveEvents(List<Event> newEvents, List<EventEntity> removeList) {
        removeList.forEach(action -> {
            TypeOfEvent concreteEvent = createTypeOfEvent(Event.EventAction.REMOVE);
            setAction(concreteEvent, action);
            concreteEvent.setEventDate(System.currentTimeMillis()/1000);
            newEvents.add(concreteEvent);
        });
    }

    private void createNewAddEvents(List<Event> newEvents, List<EventEntity> addList) {
        addList.forEach(action -> {
            TypeOfEvent concreteEvent = createTypeOfEvent(Event.EventAction.ADD);
            setAction(concreteEvent, action);
            concreteEvent.setEventDate(System.currentTimeMillis()/1000);
            newEvents.add(concreteEvent);
        });
    }

    private List<EventEntity> getListOfRemovedEventEntities(List<EventEntity> oneList, List<EventEntity> twoList) {
        return oneList.stream().filter(a -> {
                boolean exist = false;
                for (EventEntity eventEntity : twoList) {
                    if(a.equals(eventEntity)) {
                        exist = true;
                        break;
                    }
                }
                return !exist;
            }).collect(Collectors.toList());
    }

    private List<EventEntity> getListOfAddedEventEntities(List<EventEntity> oneList, List<EventEntity> twoList) {
        return twoList.stream().filter(a -> {
                boolean exist = false;
                for (EventEntity eventEntity : oneList) {
                    if(a.equals(eventEntity)) {
                        exist = true;
                        break;
                    }
                }
                return !exist;
            }).collect(Collectors.toList());
    }

    private void addEventsToSnapshotList(List<Event> concreteEvents, List<EventEntity> oneList) {
        concreteEvents.forEach(event -> {
            TypeOfEvent concreteEvent = (TypeOfEvent) event;
            if(concreteEvent.getEventAction() == Event.EventAction.ADD) {
                oneList.add(getConcreteEventEntity(concreteEvent));
            } else {
                oneList.removeIf(video -> video.equals(getConcreteEventEntity(concreteEvent)));
            }
        });
    }

    private List<Event> getConcreteEvents(List<Event> events, EventType eventType) {
        return events
                    .stream()
                    .filter(event -> event.getEventType() == eventType)
                    .sorted((o1, o2) -> {
                        if(o1.getEventDate() < o2.getEventDate()) return -1;
                        else if(o1.getEventDate() == o2.getEventDate()) return 0;
                        else return 1;
                    })
                    .collect(Collectors.toList());
    }

    protected abstract void setAction(TypeOfEvent concreteEvent, EventEntity action);

    protected abstract EventEntity getConcreteEventEntity(TypeOfEvent concreteEvent);

    protected abstract List<EventEntity> getListOfEventEntity(SnapshotCast snapshotCast);

    protected abstract TypeOfEvent createTypeOfEvent(Event.EventAction eventAction);
}
