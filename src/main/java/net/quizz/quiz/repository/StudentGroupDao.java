package net.quizz.quiz.repository;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.template.StudentGroup;
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
public class StudentGroupDao {

    @PersistenceContext
    private EntityManager em;

    public StudentGroup getStudentGroup(int id) {
        return em.find(StudentGroup.class, id);
    }

    public void save(StudentGroup studentGroup) {
        if (studentGroup.getId() == 0) {
            em.persist(studentGroup);
        } else {
            em.merge(studentGroup);
        }
    }

    public List<StudentGroup> getStudentGroups(User user) {
        String sql = "FROM StudentGroup g WHERE g.createdBy = :createdBy";
        return em.createQuery(sql, StudentGroup.class)
                .setParameter("createdBy", user)
                .getResultList();
    }
}
