package club.esprit.backend.controllers;

import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.jwt.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import club.esprit.backend.services.CategoryServiceImp;
import club.esprit.backend.services.IPost;
import club.esprit.backend.entities.Post;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final IPost postService;
    private final CategoryServiceImp categoryService;
    private UserServiceImpl userService;
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return userService.findUserByEmail(principal.getUsername());
    }
    @PostMapping("/{categoryId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long categoryId) {
        User user = getAuthenticatedUser();
        post.setUser(user);
        return ResponseEntity.ok(postService.savePost(post, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.of(postService.getPostById(id));
    }
    @GetMapping("/by-votes")
    public ResponseEntity<List<Post>> getPostsOrderedByVotes() {
        return ResponseEntity.ok(postService.getPostsOrderedByVotes());
    }
    @GetMapping("/by-category-name/{categoryName}")
    public ResponseEntity<List<Post>> getPostsByCategoryName(@PathVariable String categoryName) {
        return ResponseEntity.ok(postService.getPostsByCategoryName(categoryName));
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        // Get the authenticated user
        User user = getAuthenticatedUser();
        // Get the post to be deleted
        Post post = postService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Post with id " + id + " not found"));

        // Check if the authenticated user is the creator of the post or an admin
        if (user.equals(post.getUser()) || user.getRole().equals(Role.ADMIN)) {
            postService.deletePost(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @PutMapping("/vote/{id}")
    public ResponseEntity<Post> updateVoteCount(@PathVariable Long id, @RequestBody int voteCount) {
        Post updatedPost = postService.updateVoteCount(id, voteCount);
        return ResponseEntity.ok(updatedPost);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post existingPost = postService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Post with id " + id + " not found"));
        existingPost.setPostName(post.getPostName());
        existingPost.setDescription(post.getDescription());
        existingPost.setUrl(post.getUrl());
        return ResponseEntity.ok(postService.udpatePost(existingPost));
    }
}