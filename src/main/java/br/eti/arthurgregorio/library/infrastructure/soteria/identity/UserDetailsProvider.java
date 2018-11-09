package br.eti.arthurgregorio.library.infrastructure.soteria.identity;

import java.util.Optional;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
public interface UserDetailsProvider {

    /**
     *
     * @param username
     * @return
     */
    Optional<? extends UserDetails> findUserDetailsByUsername(String username);
}
