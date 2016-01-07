package serverdaemon.controller;

import shared.controller.api_service.IApiService;
import shared.controller.db_service.IDBService;
import shared.model.snapshots.VideoListSnapshot;
import shared.model.user.Follower;
import shared.model.user.Following;
import shared.model.video.Video;

import java.util.List;

public class VideoRefresher implements Refreshable<VideoListSnapshot> {

    private IApiService apiService;
    private IDBService dbService;

    public VideoRefresher(IApiService apiService, IDBService dbService) {
        this.apiService = apiService;
        this.dbService = dbService;
    }

    @Override
    public VideoListSnapshot refresh(Following following, Follower follower) {
        VideoListSnapshot videoListSnapshot = new VideoListSnapshot();
        List<Video> video = apiService.requestVideo(following.getUserInfo().getVkId(), follower.getAccessToken());

        videoListSnapshot.setVideoList(video);
        videoListSnapshot.setSnapshotDate(System.currentTimeMillis()/1000);

        return videoListSnapshot;
    }
}
