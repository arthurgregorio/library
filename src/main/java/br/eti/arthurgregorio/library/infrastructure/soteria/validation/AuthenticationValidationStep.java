package br.eti.arthurgregorio.library.infrastructure.soteria.validation;

import br.eti.arthurgregorio.library.infrastructure.soteria.identity.UserDetails;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
public interface AuthenticationValidationStep {

    /**
     *
     * @param userDetails
     */
    void validate(UserDetails userDetails);
}
