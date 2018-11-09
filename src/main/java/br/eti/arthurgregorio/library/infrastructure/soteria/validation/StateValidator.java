package br.eti.arthurgregorio.library.infrastructure.soteria.validation;

import br.eti.arthurgregorio.library.infrastructure.soteria.identity.UserDetails;
import br.eti.arthurgregorio.library.infrastructure.soteria.exceptions.UserInactiveException;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
public class StateValidator implements AuthenticationValidationStep {

    /**
     * {@inheritDoc}
     *
     * @param userDetails
     */
    @Override
    public void validate(UserDetails userDetails) {
        if (!userDetails.isActive()) {
            throw new UserInactiveException("error.identity.user-inactive", userDetails.getUsername());
        }
    }
}
