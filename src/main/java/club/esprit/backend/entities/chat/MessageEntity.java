package club.esprit.backend.entities.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="Message")
@Table(name = "MESSAGES")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ms_id")
    private long ms_id;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "sender")
    private String sender;

    @Column(name = "t_stamp")
    private String t_stamp;

    @Column(name = "content")
    private String content;
}
