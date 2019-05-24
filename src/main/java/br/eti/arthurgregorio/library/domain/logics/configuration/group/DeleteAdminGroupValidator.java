package br.eti.arthurgregorio.library.domain.logics.configuration.group;

import br.eti.arthurgregorio.library.domain.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.logics.BusinessLogic;

import javax.enterprise.context.Dependent;

/**
 * {@link BusinessLogic} to run if you are deleting the admin group
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 09/08/2018
 */
@Dependent
public class DeleteAdminGroupValidator implements GroupDeletingLogic {

    /**
     * {@inheritDoc }
     *
     * @param value
     */
    @Override
    public void run(Group value) {
        if (value.isAdministrator()) {
            throw new BusinessLogicException("error.group.delete-administrator");
        }
    }
}
