package org.example.scoresservice;
import org.example.scoresservice.Score;

import java.util.List;

public interface ScoreRepository {
    List<Score> getTop5Scores();
    Score addScore(Score score);
}
