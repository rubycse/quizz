package net.quizz.quiz.repository;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.Answer;
import net.quizz.quiz.domain.Publication;
import net.quizz.quiz.domain.Question;
import net.quizz.quiz.domain.Quiz;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    public List<Quiz> getUserQuizzes(User user) {
        return em.createQuery("SELECT q FROM Quiz q WHERE q.createdBy = :user", Quiz.class)
                .setParameter("user", user)
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

    public void save(Publication publication) {
        if (publication.getId() == 0) {
            em.persist(publication);
        } else {
            em.merge(publication);
        }
    }

    public Publication getPublicationByQuiz(Quiz quiz) {
        List<Publication> publications = em.createQuery("FROM Publication p WHERE p.quiz = :quiz", Publication.class)
                .setParameter("quiz", quiz)
                .getResultList();

        return publications.size() == 0 ? null : publications.get(0);
    }

    public Set<String> getUserContacts(User user) {
        List<Publication> publications = em.createQuery("FROM Publication p WHERE p.quiz.createdBy = :user", Publication.class)
                .setParameter("user", user)
                .getResultList();

        Set<String> contacts = new HashSet<>();
        for (Publication publication : publications) {
            contacts.addAll(publication.getPublishToEmails());
        }

        return contacts;
    }
}
