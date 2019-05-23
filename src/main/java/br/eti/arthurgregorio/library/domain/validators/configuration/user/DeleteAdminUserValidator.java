package br.eti.arthurgregorio.library.domain.validators.configuration.user;

import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.validators.BusinessLogic;
import br.eti.arthurgregorio.library.infrastructure.cdi.qualifier.AuthenticatedUser;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * {@link BusinessLogic} to run if you are deleting the admin
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 09/08/2018
 */
@Dependent
public class DeleteAdminUserValidator implements UserDeletingLogic {

    @Inject
    @AuthenticatedUser
    private User principal;

    /**
     * {@inheritDoc }
     *
     * @param value
     */
    @Override
    public void run(User value) {

        final String principalUsername = this.principal.getUsername();

        // prevent to delete you own user 
        if (principalUsername.equals(value.getUsername())) {
            throw new BusinessLogicException("error.user.delete-principal");
        }

        // prevent to delete the main admin
        if (value.isAdministrator()) {
            throw new BusinessLogicException("error.user.delete-administrator");
        }
    }
}
