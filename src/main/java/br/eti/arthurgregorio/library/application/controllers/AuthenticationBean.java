package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.shiroee.auth.Credential;
import br.eti.arthurgregorio.shiroee.auth.Authenticator;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

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
    private Credential credential;

    @Inject
    private Authenticator authenticator;

    /**
     * Initialize the controler, if the user is already logged-in this method
     * return the url to the dashboard page, if not, stay in the login page
     * 
     * @return the dashboard outcome or empty to stay in the login page
     */
    public String initialize() {
        if (this.authenticator.authenticationIsNeeded()) {
            this.credential = new Credential();
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
            this.authenticator.login(this.credential);
            return "/secured/dashboard.xhtml?faces-redirect=true";
        } catch (UnknownAccountException ex) {
            this.addError(true, "error.authentication.unknow-account");
        } catch (IncorrectCredentialsException ex) {
            this.addError(true, "error.authentication.incorrect-credentials");
        } catch (AuthenticationException ex) {
            this.addError(true, "error.authentication.failed");
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
