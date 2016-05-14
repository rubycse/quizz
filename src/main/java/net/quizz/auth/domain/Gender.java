package net.quizz.auth.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

/**
 * @author lutfun
 * @since 5/7/16
 */
public enum Gender {
    MALE("Male"), FEMALE("Female"), OTHER("Other");

    Gender(String displayName) {
        this.displayName = displayName;
    }

    @Transient
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
