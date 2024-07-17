package org.example.scoresservice.repositories;

import org.example.scoresservice.models.Score;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {


    @Query("SELECT s FROM Score s ORDER BY s.score DESC")
    List<Score> findTop5ByOrderByScoreDesc(PageRequest pageRequest);



}
