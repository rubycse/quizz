package net.quizz.quiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author lutfun
 * @since 3/17/16
 */

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue
    private int id;

    private String label;

    private boolean rightAnswer;

    private boolean answered;

    public Answer() {
    }

    public Answer(String label) {
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

    public boolean isRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
