package net.quizz.quiz.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lutfun
 * @since 3/17/16
 */
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private int maxDurationInMin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxDurationInMin() {
        return maxDurationInMin;
    }

    public void setMaxDurationInMin(int maxDurationInMin) {
        this.maxDurationInMin = maxDurationInMin;
    }
}
