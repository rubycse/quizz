package net.quizz.quiz.domain.template;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lutfun
 * @since 3/17/16
 */

@Entity
@Table(name = "question_template")
public class QuestionTemplate {

    @Id
    @GeneratedValue
    private int id;

    private String label;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_template_id", nullable = false)
    private List<OptionTemplate> options;

    private int maxDurationInMin;

    private boolean required;

    public QuestionTemplate() {
    }

    public QuestionTemplate(String label) {
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

    public List<OptionTemplate> getOptions() {
        return options;
    }

    public void setOptions(List<OptionTemplate> options) {
        this.options = options;
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

    public void clearOption() {
        for (OptionTemplate optionTemplate : getOptions()) {
            optionTemplate.setRightAnswer(false);
        }
    }

    public boolean hasRightAnswer() {
        for (OptionTemplate optionTemplate : getOptions()) {
            if (optionTemplate.isRightAnswer()) {
                return true;
            }
        }

        return false;
    }
}
