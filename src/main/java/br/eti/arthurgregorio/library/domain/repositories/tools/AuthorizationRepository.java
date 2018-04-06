package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.security.Authorization;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import java.util.Optional;
import org.apache.deltaspike.data.api.Repository;

/**
 * The repository of authorization
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface AuthorizationRepository extends DefaultRepository<Authorization> {

    /**
     * 
     * @param functionality
     * @param permission
     * @return 
     */
    Optional<Authorization> findOptionalByFunctionalityAndPermission(String functionality, String permission);
}
