package tn.esprit.backend.Service;

import tn.esprit.backend.entities.Comment;
import tn.esprit.backend.entities.Post;
import tn.esprit.backend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IComment {
    Comment saveComment(Comment comment);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getAllComments();
    void deleteComment(Long id);
    List<Comment> getCommentsByPost(Post post);
    List<Comment> getCommentsByUser(User user);
    Comment updateComment(Long id, String newContent);
}