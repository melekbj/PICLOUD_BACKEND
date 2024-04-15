package tn.esprit.backend.Service;

import tn.esprit.backend.entities.Category;
import tn.esprit.backend.entities.Post;
import tn.esprit.backend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IPost {
    Post savePost(Post post, Long categoryId);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPosts();
    void deletePost(Long id);
    List<Post> getPostsByCategory(Category category);
    List<Post> getPostsByUser(User user);
    Post udpatePost(Post post);
    Post updatePostName(Long id, String newName);
    Post updatePostDescription(Long id, String newDescription);
    Post updatePostUrl(Long id, String newUrl);
}