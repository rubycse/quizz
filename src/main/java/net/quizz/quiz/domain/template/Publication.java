package net.quizz.quiz.domain.template;

import net.quizz.auth.domain.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lutfun
 * @since 10/4/16
 */
@Entity
@Table(name = "publication")
public class Publication {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "quiz_template_id")
    private QuizTemplate quizTemplate;

    @Enumerated(EnumType.STRING)
    private PublishFor publishFor;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "publish_to", joinColumns = @JoinColumn(name = "publication_id"))
    @Column(name = "email", length = 64)
    private List<String> publishToEmails;

    @ManyToOne
    @JoinColumn(name = "student_group_id")
    private StudentGroup studentGroup;

    @NotNull
    @Min(1)
    private int durationInMin;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleFrom;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleTo;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultPublicationTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedOn;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "published_by_id")
    private User publishedBy;

    public Publication() {
        publishToEmails = new ArrayList<>();
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

    public PublishFor getPublishFor() {
        return publishFor;
    }

    public void setPublishFor(PublishFor publishFor) {
        this.publishFor = publishFor;
    }

    public List<String> getPublishToEmails() {
        return publishToEmails;
    }

    public void setPublishToEmails(List<String> publishToEmails) {
        this.publishToEmails = publishToEmails;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(int durationInMin) {
        this.durationInMin = durationInMin;
    }

    public Date getScheduleFrom() {
        return scheduleFrom;
    }

    public void setScheduleFrom(Date scheduleFrom) {
        this.scheduleFrom = scheduleFrom;
    }

    public Date getScheduleTo() {
        return scheduleTo;
    }

    public void setScheduleTo(Date scheduleTo) {
        this.scheduleTo = scheduleTo;
    }

    public Date getResultPublicationTime() {
        return resultPublicationTime;
    }

    public void setResultPublicationTime(Date resultPublicationTime) {
        this.resultPublicationTime = resultPublicationTime;
    }

    public User getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(User publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public boolean isOpen() {
        Date now = new Date();
        return !getScheduleFrom().after(now) && !getScheduleTo().before(now);
    }
}
