package net.quizz.quiz.domain;

/**
 * @author lutfun
 * @since 3/17/16
 */
public class Answer {

    private String val;
    private boolean right;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
