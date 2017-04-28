package net.quizz.quiz.domain.quiz;

import net.quizz.quiz.domain.template.OptionTemplate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lutfun
 * @since 4/22/17
 */

@Entity
@Table(name = "ques_option")
public class Option {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty
    private String label;

    private boolean rightAnswer;

    private boolean answered;

    public Option() {
    }

    public Option(OptionTemplate option) {
        this.setLabel(option.getLabel());
        this.setRightAnswer(option.isRightAnswer());
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
