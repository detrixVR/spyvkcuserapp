package shared.model.snapshots;

import shared.model.audio.Audio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class AudioListSnapshot extends Snapshot<Audio> {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Audio> audioList = new ArrayList<>();

    public List<Audio> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<Audio> audioList) {
        this.audioList = audioList;
    }

    @Override
    public List<Audio> getList() {
        return audioList;
    }
}
