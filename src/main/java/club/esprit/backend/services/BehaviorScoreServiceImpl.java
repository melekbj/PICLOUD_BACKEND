package club.esprit.backend.services;

import club.esprit.backend.entities.BehaviorScore;
import club.esprit.backend.repository.BehaviorScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BehaviorScoreServiceImpl implements IBehaviorScoreService {

    @Autowired
    private BehaviorScoreRepository behaviorScoreRepository;

    @Override
    public BehaviorScore addBehaviorScore(BehaviorScore behaviorScore) {
        return behaviorScoreRepository.save(behaviorScore);
    }

    @Override
    public BehaviorScore updateBehaviorScore(BehaviorScore behaviorScore) {
        return behaviorScoreRepository.save(behaviorScore);
    }

    @Override
    public void deleteBehaviorScore(Long id) {
        behaviorScoreRepository.deleteById(id);
    }

    @Override
    public BehaviorScore getBehaviorScore(Long id) {
        return behaviorScoreRepository.findById(id).orElse(null);
    }

    @Override
    public List<BehaviorScore> getAllBehaviorScores() {
        return behaviorScoreRepository.findAll();
    }

    @Override
    public List<BehaviorScore> getBehaviorScoresByDateRange(Date date1, Date date2) {
        return null;
    }


}
