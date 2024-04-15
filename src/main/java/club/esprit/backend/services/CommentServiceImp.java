package tn.esprit.backend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.backend.entities.Comment;
import tn.esprit.backend.entities.Post;
import tn.esprit.backend.entities.User;
import tn.esprit.backend.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CommentServiceImp implements IComment{
    private CommentRepository commentRepository;
    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment) ;
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
            return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return  commentRepository.findAll();
    }

    @Override
    public void deleteComment(Long id) {
 commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return  commentRepository.findByPost(post);
    }

    @Override
    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findAllByUser(user);
    }

    @Override
    public Comment updateComment(Long id, String newContent) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setText(newContent);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found for id: " + id);
        }
    }
}
