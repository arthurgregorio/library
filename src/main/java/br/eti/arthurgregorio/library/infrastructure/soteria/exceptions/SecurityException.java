package br.eti.arthurgregorio.library.infrastructure.soteria.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.ejb.ApplicationException;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 16/03/2018
 */
@NoArgsConstructor
@ApplicationException(rollback = true)
public class SecurityException extends RuntimeException {

    @Getter
    protected Object[] parameters;

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     */
    public SecurityException(String message) {
        super(message);
    }

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     * @param parameters the parameters to fill in the message
     */
    public SecurityException(String message, Object... parameters) {
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
    public SecurityException(String message, Throwable throwable, Object... parameters) {
        super(message, throwable);
        this.parameters = parameters;
    }
}