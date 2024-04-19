package club.esprit.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.Vote;
import club.esprit.backend.repository.VoteRepository;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class VoteServiceImp implements IVote{
private VoteRepository voteRepository;

    @Override
    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public Optional<Vote> getVoteById(Long id) {
        return  voteRepository.findById(id);
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    @Override
    public Optional<Vote> getTopVoteByPostAndUser(Post post, User user) {
        return  voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
    }

    @Override
    public Vote updateVote(Long id, Vote.VoteType newVoteType) {
        Optional<Vote> voteOptional = voteRepository.findById(id);
        if (voteOptional.isPresent()) {
            Vote vote = voteOptional.get();
            vote.setVoteType(newVoteType);
            return voteRepository.save(vote);
        } else {
            throw new RuntimeException("Vote not found for id: " + id);
        }
    }
}
