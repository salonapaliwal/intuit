package org.example.scoresservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.scoresservice.repositories.ScoreService;
import org.example.scoresservice.models.Score;
import org.example.scoresservice.repositories.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@Service
public class ScoreServiceImp implements ScoreService {

    private static final Logger logger = LoggerFactory.getLogger(ScoreServiceImp.class);

    private  final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImp(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTop5Scores() {
        try {
            List<Score> top5ByOrderByScoreDesc = scoreRepository.findTop5ByOrderByScoreDesc(PageRequest.of(0, 5));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(top5ByOrderByScoreDesc);
            logger.info("Below are the scores fetched:{}",json);
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JSON processing exception occurred: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error fetching top 5 scores", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception occurred: " + e.getMessage());
        }

    }

    public void processFileAndSaveScores(String filePath) throws IOException {
        List<Score> scores = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("playerName", "score").withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                Score score = new Score();
                score.setPlayerName(csvRecord.get("playerName"));
                score.setScore(Integer.parseInt(csvRecord.get("score")));
                scores.add(score);
            }
        }
        scoreRepository.saveAll(scores);
    }
    @Transactional()
    public ResponseEntity<?> addScore(Score score) {
        try {
            logger.info("Saving score:{}",score);
            score.setPlayerName(score.getPlayerName());
            score.setScore(score.getScore());
            Object json = scoreRepository.save(score);
            logger.info("Score saved successfully: {}", json);
            return ResponseEntity.status(HttpStatus.OK).body(json);

        }
        catch (Exception ex) {
            logger.error("Error adding Score", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    public Optional<Score> getScoreById(Long id) {
        return scoreRepository.findById(id);
    }

    public void deleteScoreById(Long id) {
        scoreRepository.deleteById(id);
    }

//    public boolean validateScore(Score score) {
//        if (score.getPlayerName() == null || score.getPlayerName().isEmpty() || score.getPlayerName().length() > 100) {
//            logger.error("Player name is invalid");
//            return false;
//        }
//        else if (score.getScore() <= 0 || score.getScore() > 10000) {
//            logger.error("Player score is invalid");// Assuming 10000 is the max reasonable score
//            return false;
//        }
//        else{
//            return true;
//        }
//    }


}


