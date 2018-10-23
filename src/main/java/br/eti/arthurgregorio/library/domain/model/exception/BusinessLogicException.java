package br.eti.arthurgregorio.library.domain.model.exception;

import lombok.Getter;

import javax.ejb.ApplicationException;

/**
 * This class represents all the business exceptions of the application
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 16/03/2018
 */
@ApplicationException
public class BusinessLogicException extends RuntimeException {

    @Getter
    private Object[] parameters;

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     */
    private BusinessLogicException(String message) {
        super(message);
    }

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     * @param parameters the parameters to fill in the message
     */
    private BusinessLogicException(String message, Object... parameters) {
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
    private BusinessLogicException(String message, Throwable throwable, Object... parameters) {
        super(message, throwable);
        this.parameters = parameters;
    }

    /**
     * Create a new instance of this exception
     *
     * @param message the message to be used
     * @return this exception
     */
    public static BusinessLogicException create(String message) {
        return BusinessLogicException.create(message);
    }

    /**
     * Create a new instance of this exception
     *
     * @param message the message to be used
     * @param parameters the parameters to be passed to the message
     * @return this exception
     */
    public static BusinessLogicException create(String message, Object... parameters) {
        return BusinessLogicException.create(message, parameters);
    }

    /**
     * Create a new instance of this exception
     *
     * @param message the message to be used
     * @param throwable the {@link Throwable} of this exception
     * @param parameters the parameters to be passed to the message
     * @return this exception
     */
    public static BusinessLogicException create(String message, Throwable throwable, Object... parameters) {
        return BusinessLogicException.create(message, throwable, parameters);
    }
}