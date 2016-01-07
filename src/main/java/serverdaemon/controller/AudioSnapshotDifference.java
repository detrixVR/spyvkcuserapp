package serverdaemon.controller;

import shared.model.audio.Audio;
import shared.model.event.AudioEvent;
import shared.model.event.Event;
import shared.model.snapshots.AudioListSnapshot;
import java.util.List;

public class AudioSnapshotDifference extends SnapshotDifference<AudioListSnapshot, Audio, AudioEvent> {
    @Override
    protected void setAction(AudioEvent concreteEvent, Audio action) {
        concreteEvent.setAudio(action);
    }

    @Override
    protected Audio getConcreteEventEntity(AudioEvent concreteEvent) {
        return concreteEvent.getAudio();
    }

    @Override
    protected List<Audio> getListOfEventEntity(AudioListSnapshot audioListSnapshot) {
        return audioListSnapshot.getAudioList();
    }

    @Override
    protected AudioEvent createTypeOfEvent(Event.EventAction eventAction) {
        return new AudioEvent(eventAction);
    }
}
