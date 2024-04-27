package club.esprit.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventTitle;
    private String eventDescription;
    private String picture;
    private int MaxParticipants;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String location;
    private Date startDate;
    private Boolean isPublic;

}
