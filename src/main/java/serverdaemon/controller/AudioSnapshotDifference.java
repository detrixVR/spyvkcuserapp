package serverdaemon.controller;

import shared.model.audio.Audio;
import shared.model.event.AudioEvent;
import shared.model.event.Event;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AudioSnapshotDifference implements SnapshotDifference {
    @Override
    public List<Event> difference(Snapshot s1, Snapshot s2) {
        List<Event> events = new ArrayList<>();

        AudioListSnapshot one = (AudioListSnapshot) s1;
        AudioListSnapshot two = (AudioListSnapshot) s2;
        Set<Audio> oneSet = one.getAudioList().stream().collect(Collectors.toSet());
        Set<Audio> twoSet = two.getAudioList().stream().collect(Collectors.toSet());
        Set<Audio> addSet = new HashSet<>(twoSet);
        addSet.removeAll(oneSet);
        Set<Audio> removeSet = new HashSet<>(oneSet);
        removeSet.removeAll(twoSet);

        addSet.forEach(audio -> {
            AudioEvent audioEvent = new AudioEvent(Event.EventAction.ADD);
            audioEvent.setAudio(audio);
            audioEvent.setEventDate(System.currentTimeMillis());
            events.add(audioEvent);
        });
        removeSet.forEach(audio -> {
            AudioEvent audioEvent = new AudioEvent(Event.EventAction.REMOVE);
            audioEvent.setAudio(audio);
            audioEvent.setEventDate(System.currentTimeMillis());
            events.add(audioEvent);
        });

        return events;
    }
}
