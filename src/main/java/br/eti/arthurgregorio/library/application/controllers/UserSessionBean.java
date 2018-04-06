package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.domain.model.entities.security.User;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.cdi.qualifier.PrincipalUsername;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

/**
 * The controller of the session bean. This class hold the current user on the
 * application and his data
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
     * Initialize the session
     */
    @PostConstruct
    protected void initialize() {
        
        final String principalUsername = String.valueOf(
                this.getSubject().getPrincipal());
        
        this.principal = this.userRepository
                .findOptionalByUsername(principalUsername)
                .orElseThrow(() -> new AuthenticationException(String.format(
                        "User %s has no local user", principalUsername)));
    }

    /**
     * @return if the current session of the user is valid or not 
     */
    public boolean isValid() {
        final Subject subject = this.getSubject();
        return subject.isAuthenticated() && subject.getPrincipal() != null;
    }
    
    /**
     * To check if the given role is permitted to the current user
     * 
     * @param role the role to be tested
     * @return true if is permitted, false otherwise
     */
    public boolean hasRole(String role) {
        return this.getSubject().hasRole(role);
    }
    
    /**
     * To check if the given permission is granted to the current user
     * 
     * @param permission the permission to be tested
     * @return true if is granted, false otherwise
     */
    public boolean isPermitted(String permission) {
        return this.getSubject().isPermitted(permission);
    }

    /**
     * @return return the current {@link Subject} of the application
     */
    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    
    /**
     * Simple producer to make the user object of the current principal available 
     * to other functionalities of the system, like the audit mechanism
     * 
     * @return the current principal user object
     */
    @Produces
    @PrincipalUsername 
    User producePrincipal() {
        return this.principal;
    }
}
