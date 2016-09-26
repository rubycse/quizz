package net.quizz.quiz.repository;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.Answer;
import net.quizz.quiz.domain.Question;
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
        if (quiz.getId() == 0) {
            em.persist(quiz);
        } else {
            em.merge(quiz);
        }
    }

    public Quiz getQuiz(int quizId) {
        return em.find(Quiz.class, quizId);
    }

    @SuppressWarnings("unchecked")
    public List<Quiz> getUserQuizzes(User user) {
        return (List<Quiz>) em.createQuery("SELECT q FROM Quiz q WHERE q.createdBy = :user").
                setParameter("user", user)
                .getResultList();
    }

    public Question getQuestion(int id) {
        return em.find(Question.class, id);
    }

    public void save(Question question) {
        if (question.getId() == 0) {
            em.persist(question);
        } else {
            em.merge(question);
        }
    }

    public void save(Answer answer) {
        if (answer.getId() == 0) {
            em.persist(answer);
        } else {
            em.merge(answer);
        }
    }

    public Answer getAnswer(int id) {
        return em.find(Answer.class, id);
    }

    public void deleteQuestion(int id) {
        em.remove(getQuestion(id));
    }

    public void deleteAnswer(int id) {
        em.remove(getAnswer(id));
    }
}
