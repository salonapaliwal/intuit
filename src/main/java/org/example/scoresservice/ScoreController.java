package org.example.scoresservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/top5")
    public List<Score> getTop5Scores() {
        return scoreService.getTop5Scores();
    }

    @PostMapping
    public Score addScore(@RequestBody Score score) {
        return scoreService.addScore(score);
    }
}
