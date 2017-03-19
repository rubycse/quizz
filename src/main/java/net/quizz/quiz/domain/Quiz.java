package net.quizz.quiz.domain;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.template.QuestionTemplate;
import net.quizz.quiz.domain.template.QuizTemplate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutfun
 * @since 4/21/17
 */

//@Entity
//@Table(name = "quiz")
public class Quiz {

//    @Id
//    @GeneratedValue
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "quiz_id")
    private QuizTemplate quizTemplate;

//    @ManyToOne
//    @JoinColumn(name = "answered_by_id")
    private User answeredBy;

//    @NotNull
//    @Min(1)
    private Integer durationSpentInMin;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "question_id")
    private List<QuestionTemplate> questionTemplates;

    private Date startTime;

    private Date endTime;

    public Quiz() {
    }

    public Quiz(QuizTemplate quizTemplate) {
        this.setQuizTemplate(quizTemplate);
        this.setQuestionTemplates(new ArrayList<QuestionTemplate>(quizTemplate.getQuestionTemplates().size()));
        for (QuestionTemplate questionTemplate : quizTemplate.getQuestionTemplates()) {
            this.getQuestionTemplates().add(new QuestionTemplate(questionTemplate));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public QuizTemplate getQuizTemplate() {
        return quizTemplate;
    }

    public void setQuizTemplate(QuizTemplate quizTemplate) {
        this.quizTemplate = quizTemplate;
    }

    public User getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(User answeredBy) {
        this.answeredBy = answeredBy;
    }

    public Integer getDurationSpentInMin() {
        return durationSpentInMin;
    }

    public void setDurationSpentInMin(Integer durationSpentInMin) {
        this.durationSpentInMin = durationSpentInMin;
    }

    public List<QuestionTemplate> getQuestionTemplates() {
        return questionTemplates;
    }

    public void setQuestionTemplates(List<QuestionTemplate> questionTemplates) {
        this.questionTemplates = questionTemplates;
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
}
