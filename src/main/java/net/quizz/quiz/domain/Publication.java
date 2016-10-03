package net.quizz.quiz.domain;

import javax.persistence.*;
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
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Enumerated(EnumType.STRING)
    private PublishFor publishFor;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "publish_to", joinColumns = @JoinColumn(name = "publication_id"))
    @Column(name = "email", length = 64)
    private List<String> publishToEmails;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedOn;

    public Publication() {
        publishToEmails = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }
}
