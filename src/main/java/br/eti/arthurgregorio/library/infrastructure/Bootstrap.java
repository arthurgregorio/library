package br.eti.arthurgregorio.library.infrastructure;

import br.eti.arthurgregorio.library.domain.model.entities.security.Authorization;
import br.eti.arthurgregorio.library.domain.model.entities.security.Grant;
import br.eti.arthurgregorio.library.domain.model.entities.security.Group;
import br.eti.arthurgregorio.library.domain.model.entities.security.Permissions;
import br.eti.arthurgregorio.library.domain.model.entities.security.User;
import br.eti.arthurgregorio.library.domain.repositories.tools.AuthorizationRepository;
import br.eti.arthurgregorio.library.domain.repositories.tools.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.tools.GroupRepository;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 * This class is the start point of the basic configurations for the application
 * 
 * Through here whe configure the default user and all the data that need to be
 * initialized in the database before the first start
 * 
 * This class is a EJB and runs on every start of the environment
 * 
 * TODO change this to use migrations  
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 27/02/2018
 */
@Startup
@Singleton
public class Bootstrap {

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

    /**
     * Initialize and do the job
     */
    @PostConstruct
    protected void initialize() {

        this.logger.info("Bootstraping application....");

        this.createAuthorizations();
        this.createDefaultGroup();
        this.createDefaultUser();

        this.logger.info("Bootstraping finished...");
    }

    /**
     * Synch the authorizations with the database
     */
    private void createAuthorizations() {

        final List<Authorization> authorizations
                = this.permissions.toAuthorizationList();

        authorizations.stream().forEach(authorization -> {

            final Optional<Authorization> optionalAuthz = this.authorizationRepository
                    .findOptionalByFunctionalityAndPermission(authorization
                            .getFunctionality(), authorization.getPermission());

            if (!optionalAuthz.isPresent()) {
                this.authorizationRepository.save(authorization);
            }
        });
    }

    /**
     * Create the default user group
     */
    private void createDefaultGroup() {

        final Group group = this.groupRepository
                .findOptionalByName("Administradores")
                .orElseGet(() -> new Group("Administradores"));

        if (!group.isSaved()) {

            this.logger.info("Creating default group");

            this.groupRepository.save(group);

            final List<Authorization> authorizations
                    = this.authorizationRepository.findAll();

            authorizations.stream().forEach(authorization -> {
                this.grantRepository.save(new Grant(group, authorization));
            });
        }
    }

    /**
     * Create the default user of the system
     */
    private void createDefaultUser() {

        final Optional<User> optionalUser = 
                this.userRepository.findOptionalByUsername("admin");

        if (!optionalUser.isPresent()) {
            
            this.logger.info("Creating default user");

            final Group group = this.groupRepository
                    .findOptionalByName("Administradores")
                    .get();
            
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
