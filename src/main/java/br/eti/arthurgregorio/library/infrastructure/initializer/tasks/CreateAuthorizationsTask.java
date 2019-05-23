package br.eti.arthurgregorio.library.infrastructure.initializer.tasks;

import br.eti.arthurgregorio.library.domain.model.entities.configuration.Authorization;
import br.eti.arthurgregorio.library.domain.model.entities.configuration.Permissions;
import br.eti.arthurgregorio.library.domain.repositories.configuration.AuthorizationRepository;
import br.eti.arthurgregorio.library.infrastructure.initializer.InitializationTask;
import br.eti.arthurgregorio.library.infrastructure.initializer.TransactionalInitializationTask;
import org.apache.deltaspike.core.api.exclude.Exclude;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import static org.apache.deltaspike.core.api.projectstage.ProjectStage.Production;
import static org.apache.deltaspike.core.api.projectstage.ProjectStage.SystemTest;

/**
 * {@link InitializationTask} to save all possible {@link Authorization} in the database
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 22/05/2019
 */
@Dependent
@Exclude(ifProjectStage = {Production.class, SystemTest.class})
public class CreateAuthorizationsTask extends TransactionalInitializationTask {

    @Inject
    private Permissions permissions;

    @Inject
    private AuthorizationRepository authorizationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runInsideTransaction() {
        this.permissions.toAuthorizationList().forEach(auth -> this.authorizationRepository
                .findByFunctionalityAndPermission(auth.getFunctionality(), auth.getPermission())
                .ifPresentOrElse(saved -> {/* do nothing */}, () -> this.authorizationRepository.save(auth)));
    }
}
