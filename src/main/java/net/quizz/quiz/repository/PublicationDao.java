package net.quizz.quiz.repository;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.quiz.Quiz;
import net.quizz.quiz.domain.template.Publication;
import net.quizz.quiz.domain.template.PublishFor;
import net.quizz.quiz.domain.template.QuizTemplate;
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
 * @since 6/3/17
 */

@Repository
@Transactional
public class PublicationDao {

    @PersistenceContext
    private EntityManager em;

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
        return em.createQuery("FROM Publication p WHERE :email IN elements(p.publishToEmails)" +
                " OR :email IN elements(p.studentGroup.emails)", Publication.class)
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

    public Publication getPublication(int id) {
        return em.find(Publication.class, id);
    }

    public List<Publication> getPublications(QuizTemplate quizTemplate) {
        return em.createQuery("FROM Publication p WHERE p.quizTemplate = :quizTemplate", Publication.class)
                .setParameter("quizTemplate", quizTemplate)
                .getResultList();
    }
}
