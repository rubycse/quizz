package net.quizz.domain;

import java.util.List;

/**
 * @author lutfun
 * @since 3/17/16
 */
public class QuestionAnswer {

    private String question;
    private List<Answer> answerOptions;
    private int maxDurationInMin;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<Answer> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public int getMaxDurationInMin() {
        return maxDurationInMin;
    }

    public void setMaxDurationInMin(int maxDurationInMin) {
        this.maxDurationInMin = maxDurationInMin;
    }
}
