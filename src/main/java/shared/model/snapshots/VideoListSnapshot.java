package shared.model.snapshots;

import shared.model.video.Video;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class VideoListSnapshot extends Snapshot {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Video> videoList = new ArrayList<>();

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
