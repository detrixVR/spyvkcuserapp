package shared.model.snapshots;

import javax.persistence.*;

@Entity
@Table
public class TTT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
