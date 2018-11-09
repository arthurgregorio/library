package br.eti.arthurgregorio.library.domain.repositories.configurations;

import br.eti.arthurgregorio.library.domain.model.entities.configurations.Token;
import br.eti.arthurgregorio.library.domain.model.entities.configurations.TokenType;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/11/2018
 */
@Repository
public interface TokenRepository extends DefaultRepository<Token> {

    /**
     *
     * @param rawToken
     * @return
     */
    Optional<Token> findByHash(String rawToken);

    /**
     *
     * @param hash
     * @param tokenType
     * @return
     */
    Optional<Token> findByHashAndTokenType(String hash, TokenType tokenType);
}
