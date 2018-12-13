package br.eti.arthurgregorio.library.domain.model.exception;

import lombok.Getter;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
public class WebserviceException extends ApplicationRuntimeException {

    @Getter
    private int httpStatus;

    /**
     *
     * @param message
     * @param parameters
     */
    public WebserviceException(String message, Object... parameters) {
        this(message, 500, null, parameters);
    }

    /**
     *
     * @param message
     * @param httpStatus
     * @param parameters
     */
    public WebserviceException(String message, int httpStatus, Object... parameters) {
        this(message, httpStatus, null, parameters);
    }

    /**
     *
     * @param message
     * @param throwable
     * @param httpStatus
     * @param parameters
     */
    public WebserviceException(String message, int httpStatus, Throwable throwable, Object... parameters) {
        super(message, throwable);
        this.httpStatus = httpStatus;
        this.parameters = parameters;
    }

    /**
     *
     * @return
     */
    public String getCauseAsString() {
        return this.getCause() != null ? this.getCause().getMessage() : null;
    }
}
