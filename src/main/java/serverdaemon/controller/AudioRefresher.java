package serverdaemon.controller;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.audio.Audio;
import shared.model.event.FollowerEvents;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.ArrayList;
import java.util.List;

public class AudioRefresher implements Refreshable<AudioListSnapshot> {
    private IApiService apiService;
    private IDBService dbService;

    public AudioRefresher(IApiService apiService, IDBService dbService) {
        this.apiService = apiService;
        this.dbService = dbService;
    }

    @Override
    public AudioListSnapshot refresh(Following following, Follower follower) {
        AudioListSnapshot audioListSnapshot = new AudioListSnapshot();
        FollowerEvents followerEvents = following
                .getFollower_EventsList()
                .stream()
                .filter(follower_events -> follower_events.getFollower() == follower)
                .findFirst()
                .get();
        Long addingDate = followerEvents.getAddingDate();
        ArrayList<Long> groupIds = apiService.requestGroupIds(
                following.getUserInfo().getVkId(),
                follower.getAccessToken()
        );
        List<Audio> audio = apiService.requestAudio(following.getUserInfo().getVkId(), follower.getAccessToken());

        audioListSnapshot.setAudioList(audio);
        audioListSnapshot.setSnapshotDate(System.currentTimeMillis());

        return audioListSnapshot;
    }
}
