package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.application.cdi.qualifier.AuthenticatedUser;
import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import lombok.Getter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Session controller, this class hold the current user and provide simple utilities to check his authorizations
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 08/01/2018
 */
@Named
@SessionScoped
public class UserSessionBean implements Serializable {

    @Getter
    private User principal;

    @Inject
    private UserRepository userRepository;

    /**
     * Initialize session
     */
    @PostConstruct
    protected void initialize() {

        final String principalUsername = String.valueOf(
                SecurityUtils.getSubject().getPrincipal());

        this.principal = this.userRepository.findByUsername(principalUsername)
                .orElseThrow(() -> new AuthenticationException(String.format("User %s has no local account", principalUsername)));
    }

    /**
     * To check if the given permission is granted to the current user
     *
     * @param permission the permission to be tested
     * @return true if is granted, false otherwise
     */
    public boolean isPermitted(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * CDI producer used by the application when we want an instance of the current logged-in user
     *
     * @return the current user in session
     */
    @Produces
    @AuthenticatedUser
    User producePrincipal() {
        return this.principal;
    }
}
