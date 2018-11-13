package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.domain.model.entities.configurations.Profile;
import br.eti.arthurgregorio.library.domain.model.entities.configurations.User;
import br.eti.arthurgregorio.library.domain.repositories.configurations.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.cdi.qualifier.AuthenticatedUser;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.security.Principal;

/**
 * The controller of the session bean. This class hold the current user on the application and his data
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

    @Inject
    private SecurityContext securityContext;

    /**
     * Initialize the session
     */
    @PostConstruct
    protected void initialize() {

        final Principal principal = this.securityContext.getCallerPrincipal();

        if (principal != null) {
            this.principal = this.userRepository
                    .findOptionalByUsername(principal.getName())
                    .orElseThrow(() -> new IllegalStateException(String.format("User %s has no local user", principal)));
        }
    }

    /**
     * @return the current user profile
     */
    public Profile getPrincipalProfile() {
        return this.principal.getProfile();
    }

    /**
     * To check if the given permission is granted to the current user
     *
     * @param permission the permission to be tested
     * @return true if is granted, false otherwise
     */
    public boolean isPermitted(String permission) {
        return this.securityContext.isCallerInRole(permission);
    }

    /**
     * Simple producer to make the user object of the current principal available to other functionalities of the
     * system, like the audit mechanism
     *
     * @return the current principal user object
     */
    @Produces
    @AuthenticatedUser
    User producePrincipal() {
        return this.principal;
    }
}
