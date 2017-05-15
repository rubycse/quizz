package net.quizz.quiz.domain.quiz;

import net.quizz.auth.domain.User;
import net.quizz.quiz.domain.template.Publication;
import net.quizz.quiz.domain.template.QuestionTemplate;
import net.quizz.quiz.domain.template.QuizTemplate;
import org.joda.time.DateTime;
import org.joda.time.Period;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutfun
 * @since 4/21/17
 */

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_template_id")
    private QuizTemplate quizTemplate;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "answered_by_id")
    private User answeredBy;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_id", nullable = false)
    private List<Question> questions;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public Quiz() {
    }

    public Quiz(Publication publication) {
        this.setPublication(publication);
        QuizTemplate quizTemplate = publication.getQuizTemplate();
        this.setQuizTemplate(quizTemplate);
        this.setQuestions(new ArrayList<Question>(quizTemplate.getQuestionTemplates().size()));
        for (QuestionTemplate questionTemplate : quizTemplate.getQuestionTemplates()) {
            this.getQuestions().add(new Question(questionTemplate));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuizTemplate getQuizTemplate() {
        return quizTemplate;
    }

    public void setQuizTemplate(QuizTemplate quizTemplate) {
        this.quizTemplate = quizTemplate;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public User getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(User answeredBy) {
        this.answeredBy = answeredBy;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

    public Question getQuestion(int index) {
        return getQuestions() == null || getQuestions().size() <= index ?
                null : getQuestions().get(index);
    }

    public Question getCurrentQuestion() {
        for (Question question : getQuestions()) {
            if (question.getStartTime() == null || question.getEndTime() == null) {
                return question;
            }
        }

        return null;
    }

    public int getIndex(Question question) {
        for(int index = 0; index < getQuestions().size(); index++) {
            if (getQuestions().get(index).getId() == question.getId()) {
                return index;
            }
        }

        throw new IllegalArgumentException("Question id:" + question.getId() + " does not belong to this quiz.");
    }

    public String getStartTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        return sdf.format(getStartTime());
    }

    public boolean isExpired() {
        if (getStartTime() != null) {
            DateTime startTime = new DateTime(getStartTime());
            DateTime expectedEndTime = startTime.plusMinutes(publication.getDurationInMin());
            DateTime now = new DateTime();
            if (expectedEndTime.isBefore(now)) {
                return false;
            }
        }

        return false;
    }

    public boolean isCompleted() {
        return getStartTime() != null && getEndTime() != null;
    }

    private int getRightAnswerCount() {
        int rightAnswerCount = 0;
        for (Question question : getQuestions()) {
            if (question.isRightAnswered()) {
                rightAnswerCount++;
            }
        }

        return rightAnswerCount;
    }

    public String getDuration() {
        Period period = new Period(new DateTime(getStartTime()), new DateTime(getEndTime()));
        return String.valueOf(period.getMinutes()) + " Min";
    }

    public String getScore() {
        return getRightAnswerCount() + " / " + getTotalQuestion();
    }

    public boolean isResultPublished() {
        return !getPublication().getResultPublicationTime().after(new Date());
    }

    public int getTotalQuestion() {
        return getQuestions().size();
    }
}
