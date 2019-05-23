package br.eti.arthurgregorio.library.domain.repositories.configuration;

import br.eti.arthurgregorio.library.domain.entities.configuration.Grant;
import br.eti.arthurgregorio.library.domain.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.repositories.LazyDefaultRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * The {@link Grant} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface GrantRepository extends LazyDefaultRepository<Grant> {

    /**
     * Find a list o {@link Grant} from a given {@link Group}
     *
     * @param group the {@link Group} to list his {@link Grant}
     * @return a {@link List} of {@link Grant}
     */
    List<Grant> findByGroup(Group group);
}
