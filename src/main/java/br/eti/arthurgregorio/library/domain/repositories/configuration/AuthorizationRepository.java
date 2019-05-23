package br.eti.arthurgregorio.library.domain.repositories.configuration;

import br.eti.arthurgregorio.library.domain.entities.configuration.Authorization;
import br.eti.arthurgregorio.library.domain.repositories.LazyDefaultRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

/**
 * The {@link Authorization} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface AuthorizationRepository extends LazyDefaultRepository<Authorization> {

    /**
     * Find an {@link Authorization} by the functionality and the permission
     *
     * @param functionality the functionality of the {@link Authorization}
     * @param permission the permission of the {@link Authorization}
     * @return an {@link Optional} of the {@link Authorization}
     */
    Optional<Authorization> findByFunctionalityAndPermission(String functionality, String permission);
}
