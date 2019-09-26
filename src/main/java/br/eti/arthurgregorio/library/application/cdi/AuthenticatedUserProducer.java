package br.eti.arthurgregorio.library.application.cdi;

import br.eti.arthurgregorio.library.application.cdi.qualifier.AuthenticatedUser;
import br.eti.arthurgregorio.library.application.components.CurrentUser;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import org.apache.shiro.SecurityUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * CDI producer to create an instance of {@link CurrentUser} to represent the current authenticated user
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 19/08/2019
 */
@SessionScoped
public class AuthenticatedUserProducer implements Serializable {

    private CurrentUser authenticated;

    @Inject
    private UserRepository userRepository;

    /**
     * Constructor...
     */
    @PostConstruct
    protected void initialize() {

        final Object principal = SecurityUtils.getSubject().getPrincipal();

        this.authenticated = this.userRepository.findByUsername(String.valueOf(principal))
                .map(CurrentUser::new)
                .orElseGet(CurrentUser::new);
    }

    /**
     * Produce the {@link CurrentUser} instance
     *
     * @return CurrentUser instance to be used
     */
    @Produces
    @RequestScoped
    @AuthenticatedUser
    CurrentUser produce() {

        if (this.authenticated.isEmpty()) {
            this.initialize();
        }

        return this.authenticated;
    }
}
