package club.esprit.backend.repository.libary;

import club.esprit.backend.entities.libary.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategotyRepo extends JpaRepository<Category, Integer> {
}
