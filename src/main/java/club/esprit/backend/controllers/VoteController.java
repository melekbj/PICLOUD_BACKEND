package club.esprit.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.esprit.backend.services.VoteServiceImp;
import club.esprit.backend.entities.Vote;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteServiceImp voteService;

    public VoteController(VoteServiceImp voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        return ResponseEntity.ok(voteService.saveVote(vote));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVoteById(@PathVariable Long id) {
        return ResponseEntity.of(voteService.getVoteById(id));
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        voteService.deleteVote(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable Long id, @RequestBody Vote.VoteType newVoteType) {
        Vote existingVote = voteService.getVoteById(id)
                .orElseThrow(() -> new RuntimeException("Vote with id " + id + " not found"));
        existingVote.setVoteType(newVoteType);
        return ResponseEntity.ok(voteService.updateVote(id, newVoteType));
    }
}