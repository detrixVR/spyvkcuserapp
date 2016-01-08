package handler.snapshot_building;

import com.google.inject.Inject;
import shared.controller.api_service.IApiService;
import shared.model.audio.Audio;
import shared.model.snapshots.AudioListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.util.List;

public class AudioSnapshotBuilder implements SnapshotBuilder<AudioListSnapshot> {
    private IApiService apiService;

    @Inject
    public AudioSnapshotBuilder(IApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public AudioListSnapshot build(Following following, Follower follower) {
        AudioListSnapshot audioListSnapshot = new AudioListSnapshot();
        List<Audio> audio = apiService.requestAudio(following.getUserInfo().getVkId(), follower.getAccessToken());
        if(audio == null) {
            throw new NullPointerException();
        }
        audioListSnapshot.setAudioList(audio);
        audioListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return audioListSnapshot;
    }
}
