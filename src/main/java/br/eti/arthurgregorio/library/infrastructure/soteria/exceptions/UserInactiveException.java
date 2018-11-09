package br.eti.arthurgregorio.library.infrastructure.soteria.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
@NoArgsConstructor
public class UserInactiveException extends SecurityException {

    /**
     * Constructor...
     *
     * @param message the message to describe the error
     * @param parameters the parameters to fill in the message
     */
    public UserInactiveException(String message, Object... parameters) {
        super(message, parameters);
    }
}
