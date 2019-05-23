package br.eti.arthurgregorio.library.infrastructure.initializer.tasks;

import br.eti.arthurgregorio.library.domain.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GroupRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.initializer.InitializationTask;
import br.eti.arthurgregorio.library.infrastructure.initializer.TransactionalInitializationTask;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage.Production;
import org.apache.deltaspike.core.api.projectstage.ProjectStage.SystemTest;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * {@link InitializationTask} used to create the default admin {@link User}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 22/05/2019
 */
@Dependent
@Exclude(ifProjectStage = {Production.class, SystemTest.class})
public class CreateAdminUserTask extends TransactionalInitializationTask {

    @Inject
    private UserRepository userRepository;
    @Inject
    private GroupRepository groupRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runInsideTransaction() {
        this.userRepository.findByUsername("admin").ifPresentOrElse(user -> {/* do nothing */}, () -> {

            final Group group = this.groupRepository
                    .findByName("Administradores")
                    .orElseThrow(() -> new IllegalStateException("Can't find the Administrators group"));

            final User user = new User();

            user.setName("Administrador");
            user.setEmail("contato@arthurgregorio.eti.br");
            user.setUsername("admin");
            user.setPassword(this.passwordEncoder.encryptPassword("admin"));
            user.setGroup(group);

            this.userRepository.save(user);
        });
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public int getPriority() {
        return 2;
    }
}
