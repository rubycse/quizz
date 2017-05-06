package net.quizz.quiz.domain.quiz;

import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * @author lutfun
 * @since 5/6/17
 */
public class Result {
    private Quiz quiz;
    private int totalQuestionCount;
    private int rightAnswerCount;
    private String duration;

    public Result(Quiz quiz) {
        this.quiz = quiz;
        this.totalQuestionCount = quiz.getQuestions().size();
        this.rightAnswerCount = getRightAnswerCount(quiz);
        Period period = new Period(new DateTime(quiz.getStartTime()), new DateTime(quiz.getEndTime()));
        this.duration = String.valueOf(period.getMinutes()) + " Min";
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public void setTotalQuestionCount(int totalQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
    }

    public int getRightAnswerCount() {
        return rightAnswerCount;
    }

    public void setRightAnswerCount(int rightAnswerCount) {
        this.rightAnswerCount = rightAnswerCount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private int getRightAnswerCount(Quiz quiz) {
        int rightAnswerCount = 0;
        for (Question question : quiz.getQuestions()) {
            if (question.isRightAnswered()) {
                rightAnswerCount++;
            }
        }

        return rightAnswerCount;
    }
}
