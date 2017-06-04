package net.quizz.common.exception;

import javax.ejb.ApplicationException;

/**
 * @author lutfun
 * @since 6/4/17.
 */
@ApplicationException(rollback = true)
public class InsufficientPrivilegeException extends RuntimeException {

    private static final String ERROR_KEY = "error.insufficient.privilege";

    private String messageKey;

    public InsufficientPrivilegeException() {
        this(ERROR_KEY);
    }

    public InsufficientPrivilegeException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
