package shared.model.audio;

import javax.persistence.*;

@Entity
@Table(name = "audio")
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vkId = 0L;

    private String artist = "";

    private String title = "";

    private String url = "";

    public Long getVkId() {
        return vkId;
    }

    public void setVkId(Long vkId) {
        this.vkId = vkId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
