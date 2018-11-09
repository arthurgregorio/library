package br.eti.arthurgregorio.library.infrastructure.soteria.identity;

import br.eti.arthurgregorio.library.domain.model.entities.configurations.Token;
import br.eti.arthurgregorio.library.domain.model.entities.configurations.TokenType;
import br.eti.arthurgregorio.library.domain.model.entities.configurations.User;
import br.eti.arthurgregorio.library.domain.repositories.configurations.TokenRepository;
import br.eti.arthurgregorio.library.domain.services.TokenService;
import br.eti.arthurgregorio.library.infrastructure.soteria.exceptions.UserNotFoundException;
import org.omnifaces.util.Faces;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

import static br.eti.arthurgregorio.library.domain.model.entities.configurations.TokenType.REMEMBER_ME;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
@ApplicationScoped
public class DefaultRememberMeIdentityStore implements RememberMeIdentityStore {

    @Inject
    private TokenService tokenService;

    @Inject
    private TokenRepository tokenRepository;

    /**
     * {@inheritDoc}
     *
     * @param credential
     * @return
     */
    @Override
    public CredentialValidationResult validate(RememberMeCredential credential) {

        final Optional<Token> tokenOptional = this.tokenRepository
                .findByHashAndTokenType(credential.getToken(), REMEMBER_ME);

        if (tokenOptional.isPresent()) {
            final User user = tokenOptional.map(Token::getUser).orElseThrow(UserNotFoundException::new);
            return new CredentialValidationResult(new CallerPrincipal(user.getUsername()));
        }
        return INVALID_RESULT;
    }

    /**
     * {@inheritDoc}
     *
     * @param callerPrincipal
     * @param groups
     * @return
     */
    @Override
    public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> groups) {
        return this.tokenService.create(callerPrincipal.getName(), TokenType.REMEMBER_ME, Faces.getRemoteAddr());
    }

    /**
     * {@inheritDoc}
     *
     * @param token
     */
    @Override
    public void removeLoginToken(String token) {
        this.tokenService.remove(token);
    }
}
