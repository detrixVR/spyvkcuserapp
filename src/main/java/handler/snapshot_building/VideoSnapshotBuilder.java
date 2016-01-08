package handler.snapshot_building;

import com.google.inject.Inject;
import shared.controller.api_service.IApiService;
import shared.model.snapshots.VideoListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;
import shared.model.video.Video;

import java.util.List;

public class VideoSnapshotBuilder implements SnapshotBuilder<VideoListSnapshot> {

    private IApiService apiService;

    @Inject
    public VideoSnapshotBuilder(IApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public VideoListSnapshot build(Following following, Follower follower) {
        VideoListSnapshot videoListSnapshot = new VideoListSnapshot();
        List<Video> video = apiService.requestVideo(following.getUserInfo().getVkId(), follower.getAccessToken());

        videoListSnapshot.setVideoList(video);
        videoListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return videoListSnapshot;
    }
}
