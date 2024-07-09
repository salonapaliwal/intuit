package org.example.scoresservice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class ScoreService implements  ScoreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Score> getTop5Scores() {
        try {
            String jpql = "SELECT s FROM Score s ORDER BY s.score DESC";
            TypedQuery<Score> query = entityManager.createQuery(jpql, Score.class);
            query.setMaxResults(5);
            return query.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return List.<Score>of();
    }

    @Override
    @Transactional
    public Score addScore(Score score) {
        entityManager.persist(score);
        return score;
    }
}
