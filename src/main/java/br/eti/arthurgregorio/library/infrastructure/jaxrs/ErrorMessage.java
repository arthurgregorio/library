package br.eti.arthurgregorio.library.infrastructure.jaxrs;

import br.eti.arthurgregorio.library.domain.model.exception.WebserviceException;
import br.eti.arthurgregorio.library.infrastructure.i18n.MessageSource;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.Response.Status;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Simple helper class to return information about errors in any webservice call
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
public final class ErrorMessage {

    @Getter
    @Setter
    private int httpStatus;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private String cause;

    /**
     * Constructor...
     *
     * @param httpStatus the http status code
     * @param message the message telling about the error
     * @param cause the possible cause of the error
     */
    private ErrorMessage(int httpStatus, String message, String cause) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.cause = cause;
    }

    /**
     * Factory method to help create the error message and fill the details from a given {@link WebserviceException}
     *
     * @param exception the exception
     * @return the error message to be used as response
     */
    public static ErrorMessage of(WebserviceException exception) {
        final String message = isNotBlank(exception.getMessage())
                ? MessageSource.get(exception.getMessage(), exception.getParameters()) : "";
        return new ErrorMessage(exception.getHttpStatus(), message, exception.getCauseAsString());
    }

    /**
     * Same as {@link #of(WebserviceException)} but this one receives a generic exception and the desired http status
     * code to be used as identification of the error
     *
     * @param exception the exception
     * @return the error message to be used as response
     */
    public static ErrorMessage of(Throwable exception, Status status) {
        final String cause = exception.getCause() != null ? exception.getCause().getMessage() : "";
        return new ErrorMessage(status.getStatusCode(), exception.getMessage(), cause);
    }
}
