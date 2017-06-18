package net.quizz.quiz.domain.template;

import net.quizz.auth.domain.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lutfun
 * @since 3/17/16
 */
@Entity
@Table(name = "quiz_template")
@XmlRootElement(name = "quizTemplate")
public class QuizTemplate {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(max = 250)
    private String name;

    private boolean complete;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_template_id", nullable = false)
    private List<QuestionTemplate> questionTemplates;

    public QuizTemplate() {
        questionTemplates = new ArrayList<>();
    }

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

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<QuestionTemplate> getQuestionTemplates() {
        return questionTemplates;
    }

    public void setQuestionTemplates(List<QuestionTemplate> questionTemplates) {
        this.questionTemplates = questionTemplates;
    }
}
