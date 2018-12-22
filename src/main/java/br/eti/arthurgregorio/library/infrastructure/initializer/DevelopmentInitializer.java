package br.eti.arthurgregorio.library.infrastructure.initializer;

import br.eti.arthurgregorio.library.domain.model.entities.configuration.*;
import br.eti.arthurgregorio.library.domain.repositories.configuration.AuthorizationRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GroupRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage.Production;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;

import static org.apache.deltaspike.core.api.projectstage.ProjectStage.SystemTest;

/**
 * The development {@link EnvironmentInitializer}
 * 
 * Create the default data to the app and is meant to be used only in development, for production initialization use the
 * {@link ProductionInitializer} with Migration from Flyway
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 20/08/2018
 */
@RequestScoped
@Exclude(ifProjectStage = {Production.class, SystemTest.class})
public class DevelopmentInitializer implements EnvironmentInitializer {

    @Inject
    private Logger logger;
    
    @Inject
    private Permissions permissions;

    @Inject
    private UserRepository userRepository;
    @Inject
    private GrantRepository grantRepository;
    @Inject
    private GroupRepository groupRepository;
    @Inject
    private AuthorizationRepository authorizationRepository;
    
    @Inject
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserTransaction transaction;
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void initialize() {
        
        this.logger.warn("Initializing application in development mode");
        
        try {
            this.transaction.begin();
            
            this.createAuthorizations();
            this.createDefaultGroup();
            this.createDefaultUser();
            
            this.transaction.commit();
        } catch (Exception commitException) {
            try {
                this.transaction.rollback();
            } catch (Exception rollbackException) {
                throw new EJBException(rollbackException);
            }
            throw new EJBException(commitException);
        }
    }
    
    /**
     * Synch the authorizations with the database
     */
    private void createAuthorizations() {

        final List<Authorization> authorizations = this.permissions.toAuthorizationList();

        authorizations.forEach(auth -> this.authorizationRepository.findByFunctionalityAndPermission(auth.getFunctionality(), auth.getPermission())
                .ifPresentOrElse(saved -> { /* do nothing */ }, () -> this.authorizationRepository.save(auth)));
    }

    /**
     * Create the default user group
     */
    private void createDefaultGroup() {

        final Group group = this.groupRepository
                .findByName("Administradores")
                .orElseGet(() -> new Group("Administradores"));

        if (!group.isSaved()) {
            this.logger.info("Creating default group");
            this.groupRepository.save(group);

            final List<Authorization> authorizations = this.authorizationRepository.findAll();

            authorizations.forEach(authorization -> this.grantRepository.save(new Grant(group, authorization)));
        }
    }

    /**
     * Create the default user of the system
     */
    private void createDefaultUser() {

        final Optional<User> optionalUser =
                this.userRepository.findByUsername("admin");

        if (!optionalUser.isPresent()) {
            
            this.logger.info("Creating default user");

            final Group group = this.groupRepository
                    .findByName("Administradores")
                    .orElseThrow(() -> new IllegalStateException("Can't find default administrators group"));
            
            final User user = new User();
            
            user.setName("Administrador");
            user.setEmail("contato@arthurgregorio.eti.br");
            user.setUsername("admin");
            user.setPassword(this.passwordEncoder.encryptPassword("admin"));
            
            user.setGroup(group);

            this.userRepository.save(user);
        }
    }
}
