package br.eti.arthurgregorio.library.domain.exception;

import lombok.Getter;

/**
 * Exception used to specialize all errors created on the webservices layer
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
public class WebserviceException extends ApplicationRuntimeException {

    @Getter
    private int httpStatus;

    /**
     * Constructor...
     *
     * @param message the message
     * @param parameters message parameters
     */
    public WebserviceException(String message, Object... parameters) {
        this(message, 500, null, parameters);
    }

    /**
     * Constructor...
     *
     * @param message the message
     * @param httpStatus the http status for this message
     * @param parameters message parameters
     */
    public WebserviceException(String message, int httpStatus, Object... parameters) {
        this(message, httpStatus, null, parameters);
    }

    /**
     * Constructor...
     *
     * @param message the message
     * @param throwable the exception wrapper
     * @param httpStatus the http status for this message
     * @param parameters message parameters
     */
    public WebserviceException(String message, int httpStatus, Throwable throwable, Object... parameters) {
        super(message, throwable);
        this.httpStatus = httpStatus;
        this.parameters = parameters;
    }
}
