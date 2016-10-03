package net.quizz.quiz.domain;

import net.quizz.auth.domain.User;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @author lutfun
 * @since 3/17/16
 */
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private int maxDurationInMin;

    private boolean published;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Question> questions;

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

    public int getMaxDurationInMin() {
        return maxDurationInMin;
    }

    public void setMaxDurationInMin(int maxDurationInMin) {
        this.maxDurationInMin = maxDurationInMin;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
