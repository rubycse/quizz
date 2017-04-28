package net.quizz.quiz.domain.template;

import javax.persistence.*;

/**
 * @author lutfun
 * @since 3/17/16
 */

@Entity
@Table(name = "option_template")
public class OptionTemplate {

    @Id
    @GeneratedValue
    private int id;

    private String label;

    private boolean rightAnswer;

    public OptionTemplate() {
    }

    public OptionTemplate(String label) {
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
}
