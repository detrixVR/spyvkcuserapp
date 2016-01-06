package shared.model.snapshots;

import shared.model.audio.Audio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class AudioListSnapshot extends Snapshot {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Audio> audioList = new ArrayList<>();

    public List<Audio> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<Audio> audioList) {
        this.audioList = audioList;
    }
}
