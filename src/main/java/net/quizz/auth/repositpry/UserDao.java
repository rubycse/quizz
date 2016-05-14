package net.quizz.auth.repositpry;

import net.quizz.auth.domain.Credential;
import net.quizz.auth.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author lutfun
 * @since 5/7/16
 */
@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public boolean verifyUserEmail(String verificationId) {
        int updateCount = em.createQuery("UPDATE User u SET u.emailVerified = :emailVerified" +
                " WHERE u.emailVerificationId = :verificationId AND u.emailVerified = :alreadyVerified")
                .setParameter("emailVerified", true)
                .setParameter("verificationId", verificationId)
                .setParameter("alreadyVerified", false)
                .executeUpdate();
        return updateCount > 0;
    }

    @SuppressWarnings("unchecked")
    public User getAuthenticUser(Credential credential) {
        List<User> users = (List<User>) em.createQuery("FROM User u WHERE u.username = :username AND u.password = :password")
                .setParameter("username", credential.getUsername())
                .setParameter("password", credential.getPassword())
                .getResultList();
        return users != null && users.size() > 0 ? users.get(0) : null;
    }


}
