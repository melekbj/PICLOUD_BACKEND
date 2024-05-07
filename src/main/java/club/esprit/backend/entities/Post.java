package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    private String url;
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;
    private String image;
    @Transient
    private String userName;
    @Transient
    private Long commentCount;
    public String getUserName() {
        return user != null ? user.getName() : null;
    }
    @Transient
    private String categoryName;
    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }
    @PrePersist
    public void prePersist() {
        createdDate = Instant.now();
        if (user != null) {
            userName = user.getName();
        }
        if (category != null) {
            categoryName = category.getName();
        }
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;
}