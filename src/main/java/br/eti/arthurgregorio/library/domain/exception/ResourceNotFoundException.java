package br.eti.arthurgregorio.library.domain.exception;

/**
 * This class represents an error used when the webservice cant find any resource matching the request filter
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
public class ResourceNotFoundException extends WebserviceException {

    /**
     * Constructor...
     *
     * @param message the message
     */
    public ResourceNotFoundException(String message) {
        this(message, new Object() {});
    }

    /**
     * Constructor...
     *
     * @param message the message
     * @param parameters parameters to the message
     */
    public ResourceNotFoundException(String message, Object... parameters) {
        super(message, 404, parameters);
    }
}