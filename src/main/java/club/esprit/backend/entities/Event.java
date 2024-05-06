package club.esprit.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String location;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event",cascade = CascadeType.ALL )
    private List<BehaviorScore> behaviorScores;

}