package br.eti.arthurgregorio.library.infrastructure.soteria;

import br.eti.arthurgregorio.library.infrastructure.soteria.auth.FormAuthenticationMechanism;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 08/11/2018
 */
@AutoApplySession
@RememberMe(
        cookieMaxAgeSeconds = 60*60*24*14,
        isRememberMeExpression = "#{self.isRememberMe(httpMessageContext)}"
)
@LoginToContinue(
        loginPage = "/index.xhtml?continue=true",
        errorPage = "/index.xhtml?error=true",
        useForwardToLogin = false
)
@ApplicationScoped
public class BasicAuthenticationMechanism extends FormAuthenticationMechanism {

    /**
     *
     * @param identityStore
     */
    @Inject
    public BasicAuthenticationMechanism(IdentityStore identityStore) {
        super(identityStore);
    }

    /**
     *
     * @param context
     * @return
     */
    public Boolean isRememberMe(HttpMessageContext context) {
        return context.getAuthParameters().isRememberMe();
    }
}
