package net.quizz.quiz.repository;

import net.quizz.quiz.domain.Quiz;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author lutfun
 * @since 3/29/16
 */
@Repository
@Transactional
public class QuizDao {

    @PersistenceContext
    private EntityManager em;

    public void save(Quiz quiz) {
        em.persist(quiz);
    }

    public Quiz getQuiz(int quizId) {
        return em.find(Quiz.class, quizId);
    }

    public List<Quiz> getAllQuizzes() {
        Query query = em.createQuery("SELECT q FROM Quiz q");
        return (List<Quiz>) query.getResultList();
    }
}
