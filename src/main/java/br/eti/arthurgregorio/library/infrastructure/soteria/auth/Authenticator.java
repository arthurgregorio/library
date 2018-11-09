package br.eti.arthurgregorio.library.infrastructure.soteria.auth;

import br.eti.arthurgregorio.library.infrastructure.soteria.exceptions.LogoutException;
import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import java.io.Serializable;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 08/11/2018
 */
@Dependent
public class Authenticator implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @Inject
    @Param(name = "continue")
    private boolean loginToContinue;

    /**
     *
     * @param credential
     * @return
     */
    public void login(UsernamePasswordCredential credential) throws AuthenticationException {
        this.login(credential, false);
    }

    /**
     *
     * @param credential
     * @param rememberMe
     * @return
     */
    public void login(UsernamePasswordCredential credential, boolean rememberMe) throws AuthenticationException {

        final AuthenticationParameters parameters = AuthenticationParameters.withParams()
                .credential(credential)
                .rememberMe(rememberMe)
                .newAuthentication(!this.loginToContinue);

        final AuthenticationStatus status = this.securityContext
                .authenticate(Faces.getRequest(), Faces.getResponse(), parameters);

        if (status != AuthenticationStatus.SUCCESS) {
            throw new AuthenticationException(String.format("Can't authenticate user %s", credential.getCaller()));
        }
    }

    /**
     *
     */
    public void logout() {
        try {
            Faces.logout();
            Faces.invalidateSession();
        } catch (ServletException ex) {
            throw new LogoutException(String.format("Can't authenticate user %s",
                    this.securityContext.getCallerPrincipal().getName()));
        }
    }

    /**
     *
     * @return
     */
    public boolean needToAuthenticate() {
        return this.securityContext.getCallerPrincipal() == null;
    }
}
