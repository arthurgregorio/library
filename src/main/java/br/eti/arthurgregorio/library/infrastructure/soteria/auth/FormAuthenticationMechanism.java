package br.eti.arthurgregorio.library.infrastructure.soteria.auth;

import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 08/11/2018
 */
public class FormAuthenticationMechanism implements HttpAuthenticationMechanism {

    private IdentityStore identityStore;

    /**
     * Basic constructor
     *
     * @param identityStore
     */
    public FormAuthenticationMechanism(IdentityStore identityStore) {
        this.identityStore = identityStore;
    }

    /**
     * {@inheritDoc}
     *
     * @param request
     * @param response
     * @param context
     * @return
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) {

        final Credential credential = context.getAuthParameters().getCredential();

        if (credential != null) {
            return context.notifyContainerAboutLogin(this.identityStore.validate(credential));
        }
        return context.doNothing();
    }
}