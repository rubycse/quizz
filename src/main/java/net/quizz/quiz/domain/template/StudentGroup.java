package net.quizz.quiz.domain.template;

import net.quizz.auth.domain.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lutfun
 * @since 5/31/17
 */

@Entity
@Table(name = "student_group")
public class StudentGroup {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty
    private String name;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "group_email", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "email", length = 64)
    private List<String> emails;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getEmailsStr() {
        return StringUtils.collectionToDelimitedString(emails, ", ");
    }
}
