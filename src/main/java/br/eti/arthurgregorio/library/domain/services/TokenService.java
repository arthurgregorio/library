package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.domain.model.entities.configurations.Token;
import br.eti.arthurgregorio.library.domain.model.entities.configurations.TokenType;
import br.eti.arthurgregorio.library.domain.model.entities.configurations.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.configurations.TokenRepository;
import br.eti.arthurgregorio.library.domain.repositories.configurations.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.soteria.hash.Algorithm;
import br.eti.arthurgregorio.library.infrastructure.soteria.hash.HashGenerator;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

import static br.eti.arthurgregorio.library.infrastructure.soteria.hash.Algorithm.AlgorithmType.BCRYPT;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/11/2018
 */
@ApplicationScoped
public class TokenService {

    @Inject
    private Logger logger;

    @Inject
    @Algorithm(BCRYPT)
    private HashGenerator hashGenerator;

    @Inject
    private UserRepository userRepository;
    @Inject
    private TokenRepository tokenRepository;

    /**
     *
     * @param username
     * @param tokenType
     * @param ipAddress
     * @return
     */
    @Transactional
    public String create(String username, TokenType tokenType, String ipAddress) {

        final String rawToken = UUID.randomUUID().toString();

        final User user = this.userRepository.findOptionalByUsername(username)
                .orElseThrow(() -> new BusinessLogicException("error.token.user-not-found"));

        this.tokenRepository.save(new Token(rawToken, ipAddress, tokenType, user));

        return rawToken;
    }

    /**
     *
     * @param rawToken
     */
    @Transactional
    public void remove(String rawToken) {

        final Token token = this.tokenRepository.findByHash(rawToken)
                .orElseThrow(() -> new BusinessLogicException("error.token.token-not-found"));

        this.tokenRepository.remove(token);
    }

    /**
     *
     */
    @Transactional
    public void removeExpired() {

        this.logger.info("Removing all expired tokens now...");

        // TODO remove all expired tokens
    }
}
