package org.example.scoresservice;

import org.example.scoresservice.models.Score;
import org.example.scoresservice.repositories.ScoreRepository;
import org.example.scoresservice.service.ScoreServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ScoreServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(ScoreServiceImp.class);

    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private ScoreServiceImp scoreServiceImp; // Provide the concrete implementation

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testAddScore() {
        Score score = new Score();
        score.setPlayerName("Player1");
        score.setScore(3000); // Mock repository save method

        when(scoreRepository.save(any(Score.class))).thenReturn(score);
        ResponseEntity<?> savedScore = scoreServiceImp.addScore(score); // Call service method

        Assertions.assertNotNull(savedScore);
        Assertions.assertEquals(HttpStatus.OK, savedScore.getStatusCode());
        Assertions.assertEquals(score, savedScore.getBody());
    }

    @Test
    void testAddScoreValidationFailure() {
        Score invalidScore = new Score();
        invalidScore.setPlayerName("");
        invalidScore.setScore(3000);

        ResponseEntity<?> response = scoreServiceImp.addScore(invalidScore);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("An error occurred while processing the request", response.getBody());
    }

    @Test
    void testGetTop5Scores() {
        List<Score> topScores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Score score = new Score();
            score.setId((long) i);
            score.setPlayerName("Player" + i);
            score.setScore(3000 - i * 100);
            topScores.add(score);
        }

        when(scoreRepository.findTop5ByOrderByScoreDesc(PageRequest.of(0, 5))).thenReturn(topScores);

        ResponseEntity<?> response = scoreServiceImp.getTop5Scores();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(topScores, response.getBody());
    }

    @Test
    void testAddScoreExceptionHandling() {
        Score scoreToAdd = new Score();
        scoreToAdd.setPlayerName("Player1");
        scoreToAdd.setScore(3000);

        when(scoreRepository.save(any(Score.class))).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = scoreServiceImp.addScore(scoreToAdd);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("An error occurred while processing the request", response.getBody());
    }
}
