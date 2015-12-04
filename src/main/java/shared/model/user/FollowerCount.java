package shared.model.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "follower_count")
public class FollowerCount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Follower follower;

    public FollowerCount() {}

    public FollowerCount(Follower follower, Integer count) {
        this.count = count;
        this.follower = follower;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }
}
