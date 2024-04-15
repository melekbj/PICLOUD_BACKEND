package tn.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.backend.entities.Category;
import tn.esprit.backend.entities.Post;
import tn.esprit.backend.entities.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategory(Category category);

    List<Post> findByUser(User user);


}
