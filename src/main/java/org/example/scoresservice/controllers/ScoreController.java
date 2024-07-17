package org.example.scoresservice.controllers;

import org.example.scoresservice.models.Score;
import org.example.scoresservice.repositories.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/")
@Validated
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/top5")
    public ResponseEntity<?> getTop5Scores()  {
        // Get top 5 scores
        ResponseEntity<?> topScores =  scoreService.getTop5Scores();
        return ResponseEntity.ok(topScores);
    }

    @PostMapping("/upload-scores")
    // upload a file of scores
    public String uploadScores(@RequestParam("file") MultipartFile file) {
        // Save the uploaded file to a temporary location
        File tempFile = new File(System.getProperty("java.io.tmpdir"), Objects.requireNonNull(file.getOriginalFilename()));
        try {
            file.transferTo(tempFile);
            // Process the file and save scores
            scoreService.processFileAndSaveScores(tempFile.getAbsolutePath());
            return "Scores processed successfully.";
        } catch (IOException e) {
            e.fillInStackTrace();
            return "Error processing file: " + e.getMessage();
        }
    }

    @PostMapping("/scores")
    // add a single score
    public ResponseEntity<?> addScore(@Validated @RequestBody Score score) {
        ResponseEntity<?> savedScore = scoreService.addScore(score);
        return ResponseEntity.ok(savedScore);

    }

    @DeleteMapping("score/{id}")
    // delete score by id
    public ResponseEntity<String> deleteScore(@PathVariable Long id) {
        Optional<Score> score = scoreService.getScoreById(id);
        if (score.isPresent()) {
            scoreService.deleteScoreById(id);
            return ResponseEntity.ok("Score with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
