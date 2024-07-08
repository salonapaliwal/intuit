package org.example.scoresservice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Score> getTop5Scores() {
        String jpql = "SELECT s FROM Score s ORDER BY s.score DESC";
        TypedQuery<Score> query = entityManager.createQuery(jpql, Score.class);
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Transactional
    public Score addScore(Score score) {
        entityManager.persist(score);
        return score;
    }
}
