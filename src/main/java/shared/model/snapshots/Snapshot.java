package shared.model.snapshots;

import javax.persistence.*;

@Entity
@Inheritance
public class Snapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long snapshotDate;

    public Long getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(Long snapshotDate) {
        this.snapshotDate = snapshotDate;
    }
}
