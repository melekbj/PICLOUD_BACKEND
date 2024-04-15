package tn.esprit.backend.Service;

import tn.esprit.backend.entities.Post;
import tn.esprit.backend.entities.User;
import tn.esprit.backend.entities.Vote;

import java.util.List;
import java.util.Optional;

public interface IVote {
    Vote saveVote(Vote vote);
    Optional<Vote> getVoteById(Long id);
    List<Vote> getAllVotes();
    void deleteVote(Long id);
    Optional<Vote> getTopVoteByPostAndUser(Post post, User user);
    Vote updateVote(Long id, Vote.VoteType newVoteType);
}