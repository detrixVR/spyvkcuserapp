package handler.snapshot_difference;

import org.junit.Before;
import org.junit.Test;
import shared.model.audio.Audio;
import shared.model.event.AudioEvent;
import shared.model.event.Event;
import shared.model.event.EventType;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SnapshotDifferenceTest {
    private SnapshotDifference snapshotDifference;
    private Snapshot oldS;
    private Snapshot newS;
    private List<Event> events;
    private EventType eventType;

    @Before
    public void setUp() {
        snapshotDifference = new AudioSnapshotDifference();
        oldS = mock(AudioListSnapshot.class);
        newS = mock(AudioListSnapshot.class);
        events = new ArrayList<>();
        eventType = EventType.AUDIO;
    }

    @Test
    public void testDifference() throws Exception {
        Audio one = mock(Audio.class);
        when(one.getVkId()).thenReturn(1L);
        Audio two = mock(Audio.class);
        when(two.getVkId()).thenReturn(2L);
        Audio three = mock(Audio.class);
        when(three.getVkId()).thenReturn(3L);

        List<Audio> oldSList = new ArrayList<>(Arrays.asList(one, two));
        List<Audio> newSList = new ArrayList<>(Arrays.asList(one, two, three));
        when(((AudioListSnapshot)oldS).getAudioList()).thenReturn(oldSList);
        when(((AudioListSnapshot)newS).getAudioList()).thenReturn(newSList);

        List difference = snapshotDifference.difference(oldS, newS, events, eventType);

        assertEquals(((AudioEvent)difference.get(0)).getAudio(), three);
    }
}