package org.example.scoresservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/top5")
    public List<Score> getTop5Scores() {
        return scoreRepository.getTop5Scores();
    }

    @PostMapping
    public Score addScore(@RequestBody Score score) {
        return scoreRepository.addScore(score);
    }
}
