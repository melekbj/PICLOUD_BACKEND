package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

    Optional<Vote> findVoteByPostAndUser(Post post, User currentUser); // Modify this line

    int countByPost(Post post);

    @Query("SELECT v FROM Vote v WHERE v.post = :post AND v.user = :user")
    Optional<Vote> findVoteByPostAndUserSpecific(@Param("post") Post post, @Param("user") User user);
}
