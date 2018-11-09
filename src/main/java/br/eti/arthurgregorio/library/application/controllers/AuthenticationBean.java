package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.infrastructure.soteria.auth.Authenticator;
import br.eti.arthurgregorio.library.infrastructure.soteria.auth.CredentialHolder;
import lombok.Getter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationException;

/**
 * The authentication controller 
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 29/12/2017
 */
@Named
@ViewScoped
public class AuthenticationBean extends AbstractBean {

    @Getter
    private CredentialHolder credentialHolder;

    @Inject
    private Authenticator authenticator;

    /**
     * Initialize the controller, if the user is already logged-in this method return the url to the dashboard page,
     * if not, stay in the login page
     * 
     * @return the dashboard outcome or empty to stay in the login page
     */
    public String initialize() {
        if (this.authenticator.needToAuthenticate()) {
            this.credentialHolder = new CredentialHolder();
            return "";
        } else {
            return "/secured/dashboard.xhtml?faces-redirect=true";
        }
    }

    /**
     * Process the login request
     * 
     * @return the url of the success page
     */
    public String doLogin() {
        try {
            this.authenticator.login(this.credentialHolder.toCredential());
            return "/secured/dashboard.xhtml?faces-redirect=true";
        } catch (AuthenticationException ex) {
            this.addError(true, "error.authentication");
        }
        return null;
    }

    /**
     * Process the logout request
     * 
     * @return the url to the login page
     */
    public String doLogout() {
        this.authenticator.logout();
        return "/index.xhtml?faces-redirect=true";
    }
}
