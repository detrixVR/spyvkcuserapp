package serverdaemon.controller;

import shared.model.event.Event;
import shared.model.event.EventType;
import shared.model.event.VideoEvent;
import shared.model.snapshots.Snapshot;
import shared.model.snapshots.VideoListSnapshot;
import shared.model.video.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VideoSnapshotDifference implements SnapshotDifference {
    @Override
    public List<Event> difference(Snapshot oldS, Snapshot newS, List<Event> events) {
        List<Event> newEvents = new ArrayList<>();

        List<Event> videoEvents = events
                .stream()
                .filter(event -> event.getEventType() == EventType.VIDEO)
                .sorted((o1, o2) -> {
                    if(o1.getEventDate() < o2.getEventDate()) return -1;
                    else if(o1.getEventDate() == o2.getEventDate()) return 0;
                    else return 1;
                })
                .collect(Collectors.toList());

        VideoListSnapshot one = (VideoListSnapshot) oldS;
        VideoListSnapshot two = (VideoListSnapshot) newS;
        List<Video> oneList = new ArrayList<>(one.getVideoList());
        List<Video> twoList = two.getVideoList();

        videoEvents.forEach(event -> {
            VideoEvent videoEvent = (VideoEvent) event;
            if(videoEvent.getEventAction() == Event.EventAction.ADD) {
                oneList.add(videoEvent.getVideo());
            } else {
                oneList.removeIf(video -> video.equals(videoEvent.getVideo()));
            }
        });

        List<Video> addList = twoList.stream().filter(a -> {
            boolean exist = false;
            for (Video video : oneList) {
                if(a.equals(video)) {
                    exist = true;
                    break;
                }
            }
            return !exist;
        }).collect(Collectors.toList());
        List<Video> removeList = oneList.stream().filter(a -> {
            boolean exist = false;
            for (Video video : twoList) {
                if(a.equals(video)) {
                    exist = true;
                    break;
                }
            }
            return !exist;
        }).collect(Collectors.toList());

        addList.forEach(action -> {
            VideoEvent videoEvent = new VideoEvent(Event.EventAction.ADD);
            videoEvent.setVideo(action);
            videoEvent.setEventDate(System.currentTimeMillis());
            newEvents.add(videoEvent);
        });
        removeList.forEach(video -> {
            VideoEvent videoEvent = new VideoEvent(Event.EventAction.REMOVE);
            videoEvent.setVideo(video);
            videoEvent.setEventDate(System.currentTimeMillis());
            newEvents.add(videoEvent);
        });

        return newEvents;
    }
}
