package club.esprit.backend.entities.quiz;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String question;
    String image;
    String anecdote;
    String wikipedia;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<QuestionOption> questionOptions;
    }
