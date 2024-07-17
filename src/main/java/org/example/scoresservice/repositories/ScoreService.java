package org.example.scoresservice.repositories;

import org.example.scoresservice.models.Score;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
public interface ScoreService {
    ResponseEntity<?> addScore(Score score);
    ResponseEntity<?> getTop5Scores();
    void processFileAndSaveScores(String filePath) throws IOException;
    void deleteScoreById(Long id);
    Optional<Score> getScoreById(Long id);
}