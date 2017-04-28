package net.quizz.quiz.domain.quiz;

import net.quizz.quiz.domain.template.OptionTemplate;
import net.quizz.quiz.domain.template.QuestionTemplate;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutfun
 * @since 4/22/17
 */

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    private int id;

    private String label;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private List<Option> options;

    private boolean required;

    private int maxDurationInMin;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public Question() {
    }

    public Question(QuestionTemplate questionTemplate) {
        this.setLabel(questionTemplate.getLabel());
        this.setOptions(new ArrayList<Option>(questionTemplate.getOptions().size()));
        for (OptionTemplate optionTemplate : questionTemplate.getOptions()) {
            this.getOptions().add(new Option(optionTemplate));
        }
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getMaxDurationInMin() {
        return maxDurationInMin;
    }

    public void setMaxDurationInMin(int maxDurationInMin) {
        this.maxDurationInMin = maxDurationInMin;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void updateAnswer(List<Option> options) {
        for (Option opt : getOptions()) {
            for (Option option : options) {
                if (opt.getId() == option.getId()) {
                    opt.setAnswered(true);
                }
            }
        }
    }

    public List<Option> getAnswer() {
        List<Option> answer = new ArrayList<>();
        for (Option option : getOptions()) {
            if (option.isAnswered()) {
                answer.add(option);
            }
        }

        return answer;
    }

    public boolean isRightAnswered() {
        List<Option> rightAnswer = new ArrayList<>();
        for (Option option : getOptions()) {
            if (option.isRightAnswer() != option.isAnswered()) {
                return false;
            }
        }

        return true;
    }
}
