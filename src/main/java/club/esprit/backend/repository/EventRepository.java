package club.esprit.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Add custom query methods if needed
}
