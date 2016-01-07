package shared.model.snapshots;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance
public abstract class Snapshot<T> {
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

    public abstract List<T> getList();
}
