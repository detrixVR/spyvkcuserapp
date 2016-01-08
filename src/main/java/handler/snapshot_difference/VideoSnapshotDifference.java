package handler.snapshot_difference;

import shared.model.event.Event;
import shared.model.event.VideoEvent;
import shared.model.snapshots.VideoListSnapshot;
import shared.model.video.Video;
import java.util.List;

public class VideoSnapshotDifference extends SnapshotDifference<VideoListSnapshot, Video, VideoEvent> {
    @Override
    protected void setAction(VideoEvent concreteEvent, Video action) {
        concreteEvent.setVideo(action);
    }

    @Override
    protected Video getConcreteEventEntity(VideoEvent concreteEvent) {
        return concreteEvent.getVideo();
    }

    @Override
    protected List<Video> getListOfEventEntity(VideoListSnapshot videoListSnapshot) {
        return videoListSnapshot.getVideoList();
    }

    @Override
    protected VideoEvent createTypeOfEvent(Event.EventAction eventAction) {
        return new VideoEvent(eventAction);
    }
}
