package serverdaemon.controller.snapshot_building;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.audio.Audio;
import shared.model.event.FollowerEvents;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.ArrayList;
import java.util.List;

public class AudioSnapshotBuilder implements SnapshotBuilder<AudioListSnapshot> {
    private IApiService apiService;

    public AudioSnapshotBuilder(IApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public AudioListSnapshot build(Following following, Follower follower) {
        AudioListSnapshot audioListSnapshot = new AudioListSnapshot();
        List<Audio> audio = apiService.requestAudio(following.getUserInfo().getVkId(), follower.getAccessToken());
        audioListSnapshot.setAudioList(audio);
        audioListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return audioListSnapshot;
    }
}
