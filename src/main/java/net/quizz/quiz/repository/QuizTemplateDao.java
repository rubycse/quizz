package net.quizz.quiz.repository;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.template.OptionTemplate;
import net.quizz.quiz.domain.template.QuestionTemplate;
import net.quizz.quiz.domain.template.QuizTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author lutfun
 * @since 6/3/17
 */

@Repository
@Transactional
public class QuizTemplateDao {

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

    public void delete(QuizTemplate quizTemplate) {
        em.remove(quizTemplate);
    }
}
