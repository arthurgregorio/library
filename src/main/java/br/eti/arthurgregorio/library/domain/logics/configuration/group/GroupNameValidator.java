package br.eti.arthurgregorio.library.domain.logics.configuration.group;

import br.eti.arthurgregorio.library.domain.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.logics.BusinessLogic;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GroupRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * {@link BusinessLogic} used to validate the name of the {@link Group} to prevent duplicates
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 25/09/2019
 */
@Dependent
public class GroupNameValidator implements GroupSavingLogic, GroupUpdatingLogic {

    @Inject
    private GroupRepository groupRepository;

    /**
     * {@inheritDoc}
     *
     * @param value
     */
    @Override
    public void run(Group value) {
        if (value.isSaved()) {
            this.groupRepository.findByNameAndIdNotEqual(value.getName(), value.getId())
                    .ifPresent(this::duplicated);
        } else {
            this.groupRepository.findByName(value.getName())
                    .ifPresent(this::duplicated);
        }
    }

    /**
     *
     * @param group
     */
    private void duplicated(Group group) {
        throw new BusinessLogicException("error.group.duplicate-name");
    }
}
