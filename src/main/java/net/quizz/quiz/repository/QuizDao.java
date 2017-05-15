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

    public void save(QuizTemplate quizTemplate) {
        if (quizTemplate.getId() == 0) {
            em.persist(quizTemplate);
        } else {
            em.merge(quizTemplate);
        }
    }

    public QuizTemplate getQuizTemplate(int quizId) {
        return em.find(QuizTemplate.class, quizId);
    }

    public List<QuizTemplate> getUserQuizTemplates(User user) {
        return em.createQuery("SELECT q FROM QuizTemplate q WHERE q.createdBy = :user", QuizTemplate.class)
                .setParameter("user", user)
                .getResultList();
    }

    public QuestionTemplate getQuestionTemplate(int id) {
        return em.find(QuestionTemplate.class, id);
    }

    public void save(QuestionTemplate questionTemplate) {
        if (questionTemplate.getId() == 0) {
            em.persist(questionTemplate);
        } else {
            em.merge(questionTemplate);
        }
    }

    public void save(OptionTemplate optionTemplate) {
        if (optionTemplate.getId() == 0) {
            em.persist(optionTemplate);
        } else {
            em.merge(optionTemplate);
        }
    }

    public OptionTemplate getOption(int id) {
        return em.find(OptionTemplate.class, id);
    }

    public void deleteQuestion(int id) {
        em.remove(getQuestionTemplate(id));
    }

    public void deleteOption(int id) {
        em.remove(getOption(id));
    }

    public void save(Publication publication) {
        if (publication.getId() == 0) {
            em.persist(publication);
        } else {
            em.merge(publication);
        }
    }

    public Publication getPublicationByQuiz(QuizTemplate quizTemplate) {
        List<Publication> publications = em.createQuery("FROM Publication p WHERE p.quizTemplate = :quizTemplate", Publication.class)
                .setParameter("quizTemplate", quizTemplate)
                .getResultList();

        return publications.size() == 0 ? null : publications.get(0);
    }

    public List<Publication> getPublicationsSharedWithMe(User user) {
        return em.createQuery("FROM Publication p WHERE :email IN elements(p.publishToEmails)", Publication.class)
                .setParameter("email", user.getEmail())
                .getResultList();
    }

    public List<Publication> getAllPublicPublications() {
        return em.createQuery("FROM Publication p WHERE p.publishFor = :publishFor", Publication.class)
                .setParameter("publishFor", PublishFor.EVERYBODY)
                .getResultList();
    }

    public Set<String> getUserContacts(User user) {
        List<Publication> publications = em.createQuery("FROM Publication p WHERE p.quizTemplate.createdBy = :user", Publication.class)
                .setParameter("user", user)
                .getResultList();

        Set<String> contacts = new HashSet<>();
        for (Publication publication : publications) {
            contacts.addAll(publication.getPublishToEmails());
        }

        return contacts;
    }

    public QuizTemplate getQuizTemplate(QuestionTemplate questionTemplate) {
        Integer quizTemplateId = (Integer) em.createNativeQuery("SELECT quiz_template_id FROM question_template ques WHERE ques.id = :questionId")
                .setParameter("questionId", questionTemplate.getId())
                .getSingleResult();
        return getQuizTemplate(quizTemplateId);
    }

    public QuestionTemplate getQuestionTemplate(OptionTemplate optionTemplate) {
        Integer questionId = (Integer) em.createNativeQuery("SELECT question_template_id FROM option_template op WHERE op.id = :optionId")
                .setParameter("optionId", optionTemplate.getId())
                .getSingleResult();
        return getQuestionTemplate(questionId);
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

//    public Quiz getQuiz(Question question) {
//        Integer quizId = (Integer) em.createNativeQuery("SELECT quiz_id FROM question ques WHERE ques.id = :questionId")
//                .setParameter("questionId", question.getId())
//                .getSingleResult();
//        return getQuiz(quizId);
//    }

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

    public Publication getPublication(int id) {
        return em.find(Publication.class, id);
    }
}
