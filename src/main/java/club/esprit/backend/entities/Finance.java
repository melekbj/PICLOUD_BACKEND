package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Membership receiver;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
@JsonIgnore
    public Membership getReceiver() {
        return receiver;
    }
@JsonProperty
    public void setReceiver(Membership receiver) {
        this.receiver = receiver;
    }
    @JsonIgnore
    public Club getClub() {
        return club;
    }
    @JsonProperty
    public void setClub(Club club) {
        this.club = club;
    }
}
