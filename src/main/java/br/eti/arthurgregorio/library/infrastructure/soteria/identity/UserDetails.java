package br.eti.arthurgregorio.library.infrastructure.soteria.identity;

import java.util.Set;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
public interface UserDetails {

    /**
     *
     * @return
     */
    String getUsername();

    /**
     *
     * @return
     */
    String getPassword();

    /**
     *
     * @return
     */
    boolean isActive();

    /**
     *
     * @return
     */
    Set<String> getPermissions();

    /**
     *
     * @return
     */
    default boolean isInactive() {
        return !this.isActive();
    }
}
