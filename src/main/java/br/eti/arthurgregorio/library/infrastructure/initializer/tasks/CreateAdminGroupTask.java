package br.eti.arthurgregorio.library.infrastructure.initializer.tasks;

import br.eti.arthurgregorio.library.domain.model.entities.configuration.Authorization;
import br.eti.arthurgregorio.library.domain.model.entities.configuration.Grant;
import br.eti.arthurgregorio.library.domain.model.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.repositories.configuration.AuthorizationRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GroupRepository;
import br.eti.arthurgregorio.library.infrastructure.initializer.InitializationTask;
import br.eti.arthurgregorio.library.infrastructure.initializer.TransactionalInitializationTask;
import org.apache.deltaspike.core.api.exclude.Exclude;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

import static org.apache.deltaspike.core.api.projectstage.ProjectStage.Production;
import static org.apache.deltaspike.core.api.projectstage.ProjectStage.SystemTest;

/**
 * {@link InitializationTask} to create the default administrators {@link Group}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 22/05/2019
 */
@Dependent
@Exclude(ifProjectStage = {Production.class, SystemTest.class})
public class CreateAdminGroupTask extends TransactionalInitializationTask {

    @Inject
    private GrantRepository grantRepository;
    @Inject
    private GroupRepository groupRepository;
    @Inject
    private AuthorizationRepository authorizationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runInsideTransaction() {

        final Group group = this.groupRepository
                .findByName("Administradores")
                .orElseGet(() -> new Group("Administradores"));

        if (!group.isSaved()) {
            this.groupRepository.save(group);
            final List<Authorization> authorizations = this.authorizationRepository.findAll();
            authorizations.forEach(authorization -> this.grantRepository.save(new Grant(group, authorization)));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public int getPriority() {
        return 1;
    }
}
