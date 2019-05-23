package br.eti.arthurgregorio.library.domain.exception;

import javax.ejb.ApplicationException;

/**
 * This class represents all the business exceptions on the application, this exceptions are thrown most of the time
 * in the services layer
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 16/03/2018
 */
@ApplicationException
public class BusinessLogicException extends ApplicationRuntimeException {

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     */
    public BusinessLogicException(String message) {
        super(message);
    }

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     * @param parameters the parameters to fill in the message
     */
    public BusinessLogicException(String message, Object... parameters) {
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
    public BusinessLogicException(String message, Throwable throwable, Object... parameters) {
        super(message, throwable);
        this.parameters = parameters;
    }
}