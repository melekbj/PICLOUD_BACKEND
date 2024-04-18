package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.BehaviorScore;

public interface BehaviorScoreRepository extends JpaRepository<BehaviorScore, Long> {

}
