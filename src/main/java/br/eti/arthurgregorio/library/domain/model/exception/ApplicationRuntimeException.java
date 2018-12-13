package br.eti.arthurgregorio.library.domain.model.exception;

import lombok.Getter;

/**
 * This is the basic representation of a any exception inside the application, all exceptions should use this as base
 * class to implement another functionality
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
public class ApplicationRuntimeException extends RuntimeException {

    @Getter
    protected Object[] parameters;

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     */
    public ApplicationRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     * @param parameters the parameters to fill in the message
     */
    public ApplicationRuntimeException(String message, Object... parameters) {
        super(message);
        this.parameters = parameters;
    }

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     * @param throwable the instance of the exception to compose the stack
     * @param parameters the parameters to fill in the message
     */
    public ApplicationRuntimeException(String message, Throwable throwable, Object... parameters) {
        super(message, throwable);
        this.parameters = parameters;
    }
}
