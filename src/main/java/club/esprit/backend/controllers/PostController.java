package tn.esprit.backend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.Service.CategoryServiceImp;
import tn.esprit.backend.Service.IPost;
import tn.esprit.backend.Service.PostServiceImp;
import tn.esprit.backend.entities.Category;
import tn.esprit.backend.entities.Post;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final IPost postService;
    private final CategoryServiceImp categoryService;


    @PostMapping("/{categoryId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long categoryId) {
        return ResponseEntity.ok(postService.savePost(post, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.of(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
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