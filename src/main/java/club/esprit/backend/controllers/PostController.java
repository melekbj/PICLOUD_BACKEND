    package club.esprit.backend.controllers;

    import club.esprit.backend.entities.Role;
    import club.esprit.backend.entities.User;
    import club.esprit.backend.services.CommentServiceImp;
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
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/api/posts")
    @CrossOrigin(origins = "http://localhost:4200")
    public class PostController {

        private final IPost postService;
        private final CategoryServiceImp categoryService;
        private UserServiceImpl userService;
        private CommentServiceImp commentService;
        private User getAuthenticatedUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User principal =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            return userService.findUserByEmail(principal.getUsername());
        }
        @PostMapping("/{categoryId}")
        public ResponseEntity<Post> createPost(@RequestParam("image") MultipartFile image,
                                               @RequestParam("postName") String postName,
                                               @RequestParam("description") String description,
                                               @RequestParam("url") String url,
                                               @PathVariable Long categoryId) {
            User user = getAuthenticatedUser();

            Post post = new Post();
            post.setPostName(postName);
            post.setDescription(description);
            post.setUrl(url);
            post.setUser(user);

            // Handle the image file
            if (image != null && !image.isEmpty()) {
                try {
                    // Save the file to a directory
                    String imagePath = "C:\\xampp\\htdocs\\img"; // Update this path
                    Path path = Paths.get(imagePath + "\\" + image.getOriginalFilename());
                    Files.write(path, image.getBytes());

                    // Set the image URL in the post object
                    post.setImage("/" + image.getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            }

            return ResponseEntity.ok(postService.savePost(post, categoryId));
        }

    //    @PostMapping("/{categoryId}")
    //    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long categoryId) {
    //        User user = getAuthenticatedUser();
    //        post.setUser(user);
    //        return ResponseEntity.ok(postService.savePost(post, categoryId));
    //    }

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
        @GetMapping("/count/{postId}")
        public ResponseEntity<Long> getCommentCountByPostId(@PathVariable Long postId) {
            Post post = postService.getPostById(postId)
                    .orElseThrow(() -> new RuntimeException("Post with id " + postId + " not found"));
            long count = commentService.getCommentCountByPost(post);
            return ResponseEntity.ok(count);
        }
        @GetMapping
        public ResponseEntity<List<Post>> getAllPosts() {
            return ResponseEntity.ok(postService.getAllPosts());
        }
        @GetMapping("/by-category/{categoryId}")
        public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable Long categoryId) {
            return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
        }
        @PutMapping("/edit/{id}")
        public ResponseEntity<Post> editPost(@PathVariable Long id, @RequestBody Post post) {
            Post updatedPost = postService.editPost(id, post);
            return ResponseEntity.ok(updatedPost);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Void>  deletePost(@PathVariable Long id) {
            postService.deletePost(id);
            return ResponseEntity.ok().build();
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
        @GetMapping("/share/{id}")
        public ResponseEntity<String> generateShareableLink(@PathVariable Long id) {
            String shareableLink = postService.generateShareableLink(id);
            return ResponseEntity.ok(shareableLink);
        }
    }