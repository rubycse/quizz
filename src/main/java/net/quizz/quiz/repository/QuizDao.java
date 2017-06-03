package net.quizz.quiz.repository;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.quiz.Question;
import net.quizz.quiz.domain.quiz.Quiz;
import net.quizz.quiz.domain.template.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lutfun
 * @since 3/29/16
 */
@Repository
@Transactional
public class QuizDao {

    @PersistenceContext
    private EntityManager em;

    public Quiz getQuiz(int quizId) {
        return em.find(Quiz.class, quizId);
    }

    public Question getQuestion(int questionId) {
        return em.find(Question.class, questionId);
    }

    public void save(Quiz quiz) {
        if (quiz.getId() == 0) {
            em.persist(quiz);
        } else {
            em.merge(quiz);
        }
    }

    public void save(Question question) {
        if (question.getId() == 0) {
            em.persist(question);
        } else {
            em.merge(question);
        }
    }

    public List<Quiz> getQuizzes(User user) {
        String sql = "FROM Quiz q WHERE q.answeredBy = :answeredBy";
        return em.createQuery(sql, Quiz.class)
                .setParameter("answeredBy", user)
                .getResultList();
    }

    public Quiz getQuiz(Publication publication, User answeredBy) {
        try {
            String sql = "FROM Quiz q WHERE q.publication = :publication AND q.answeredBy = :answeredBy";
            return em.createQuery(sql, Quiz.class)
                    .setParameter("publication", publication)
                    .setParameter("answeredBy", answeredBy)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Quiz> getQuizzes(Publication publication) {
        return em.createQuery("FROM Quiz q WHERE q.publication = :publication", Quiz.class)
                .setParameter("publication", publication)
                .getResultList();
    }
}
