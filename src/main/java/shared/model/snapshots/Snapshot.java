package shared.model.snapshots;

import javax.persistence.*;

@Entity
@Inheritance
public class Snapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_snapshot")
    private Long dateOfSnapshot;

    public Long getDateOfSnapshot() {
        return dateOfSnapshot;
    }

    public void setDateOfSnapshot(Long dateOfSnapshot) {
        this.dateOfSnapshot = dateOfSnapshot;
    }
}
