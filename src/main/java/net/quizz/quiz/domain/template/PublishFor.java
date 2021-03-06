package net.quizz.quiz.domain.template;

/**
 * @author lutfun
 * @since 10/3/16
 */

public enum PublishFor {

    EVERYBODY("Everybody"),
    SELECTED_USER("Selected User"),
    GROUP("Group");

    private String label;

    PublishFor(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
