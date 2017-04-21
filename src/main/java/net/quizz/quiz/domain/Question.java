package net.quizz.quiz.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author lutfun
 * @since 3/17/16
 */

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    private int id;

    private String label;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id", nullable = false)
    private List<Answer> answerOptions;

    private int maxDurationInMin;
    private boolean required;

    public Question() {
    }

    public Question(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Answer> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<Answer> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public int getMaxDurationInMin() {
        return maxDurationInMin;
    }

    public void setMaxDurationInMin(int maxDurationInMin) {
        this.maxDurationInMin = maxDurationInMin;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void clearAnswer() {
        for (Answer answer : getAnswerOptions()) {
            answer.setRightAnswer(false);
        }
    }
}
