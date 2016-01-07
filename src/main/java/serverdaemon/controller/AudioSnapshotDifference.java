package serverdaemon.controller;

import shared.model.audio.Audio;
import shared.model.event.AudioEvent;
import shared.model.event.Event;
import shared.model.event.EventType;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AudioSnapshotDifference implements SnapshotDifference {
    @Override
    public List<Event> difference(Snapshot oldS, Snapshot newS, List<Event> events) {
        List<Event> newEvents = new ArrayList<>();

        List<Event> audioEvents = events
                .stream()
                .filter(event -> event.getEventType() == EventType.AUDIO)
                .sorted((o1, o2) -> {
                    if(o1.getEventDate() < o2.getEventDate()) return -1;
                    else if(o1.getEventDate() == o2.getEventDate()) return 0;
                    else return 1;
                })
                .collect(Collectors.toList());

        AudioListSnapshot one = (AudioListSnapshot) oldS;
        AudioListSnapshot two = (AudioListSnapshot) newS;
        List<Audio> oneList = new ArrayList<>(one.getAudioList());
        List<Audio> twoList = two.getAudioList();

        audioEvents.forEach(event -> {
            AudioEvent audioEvent = (AudioEvent) event;
            if(audioEvent.getEventAction() == Event.EventAction.ADD) {
                oneList.add(audioEvent.getAudio());
            } else {
                oneList.removeIf(audio -> audio.equals(audioEvent.getAudio()));
            }
        });

        List<Audio> addList = twoList.stream().filter(a -> {
            boolean exist = false;
            for (Audio audio : oneList) {
                if(a.equals(audio)) {
                    exist = true;
                    break;
                }
            }
            return !exist;
        }).collect(Collectors.toList());
        List<Audio> removeList = oneList.stream().filter(a -> {
            boolean exist = false;
            for (Audio audio : twoList) {
                if(a.equals(audio)) {
                    exist = true;
                    break;
                }
            }
            return !exist;
        }).collect(Collectors.toList());

        addList.forEach(audio -> {
            AudioEvent audioEvent = new AudioEvent(Event.EventAction.ADD);
            audioEvent.setAudio(audio);
            audioEvent.setEventDate(System.currentTimeMillis());
            newEvents.add(audioEvent);
        });
        removeList.forEach(audio -> {
            AudioEvent audioEvent = new AudioEvent(Event.EventAction.REMOVE);
            audioEvent.setAudio(audio);
            audioEvent.setEventDate(System.currentTimeMillis());
            newEvents.add(audioEvent);
        });

        return newEvents;
    }
}
