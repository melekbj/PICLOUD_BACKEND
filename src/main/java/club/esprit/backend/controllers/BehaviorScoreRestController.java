package club.esprit.backend.controllers;

import club.esprit.backend.entities.BehaviorScore;
import club.esprit.backend.entities.BehaviorType;
import club.esprit.backend.services.IBehaviorScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/behavior-score")
public class BehaviorScoreRestController {
    private IBehaviorScoreService behaviorScoreService;

    @Autowired
    public BehaviorScoreRestController(IBehaviorScoreService behaviorScoreService) {
        this.behaviorScoreService = behaviorScoreService;
    }

    @PostMapping("/add")
    public BehaviorScore addBehaviorScore(@RequestBody BehaviorScore behaviorScore) {
        return behaviorScoreService.addBehaviorScore(behaviorScore);
    }

    @PutMapping("/update")
    public BehaviorScore updateBehaviorScore(@RequestBody BehaviorScore behaviorScore) {
        return behaviorScoreService.updateBehaviorScore(behaviorScore);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBehaviorScore(@PathVariable Long id) {
        behaviorScoreService.deleteBehaviorScore(id);
    }

    @GetMapping("/get/{id}")
    public BehaviorScore getBehaviorScore(@PathVariable Long id) {
        return behaviorScoreService.getBehaviorScore(id);
    }

    @GetMapping("/getAll")
    public List<BehaviorScore> getAllBehaviorScores() {
        return behaviorScoreService.getAllBehaviorScores();
    }

    @GetMapping("/getByDateRange")
    public List<BehaviorScore> getBehaviorScoresByDateRange(@RequestParam Date date1, @RequestParam Date date2) {
        return behaviorScoreService.getBehaviorScoresByDateRange(date1, date2);
    }

   /* @GetMapping("/getByType")
    public List<BehaviorScore> getBehaviorScoresByType(@RequestParam BehaviorType behaviorType) {
        return behaviorScoreService.getBehaviorScoresByType(behaviorType);
    }*/
}
