package edu.asu.diging.wic.core.exceptions;

public class CannotFindConceptTextException extends Exception {

    private static final long serialVersionUID = 1L;

    public CannotFindConceptTextException() {
        super();
    }

    public CannotFindConceptTextException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CannotFindConceptTextException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindConceptTextException(String message) {
        super(message);
    }

    public CannotFindConceptTextException(Throwable cause) {
        super(cause);
    }
}
