package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.security.Grant;
import br.eti.arthurgregorio.library.domain.model.entities.security.Group;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import java.util.List;
import org.apache.deltaspike.data.api.Repository;

/**
 * The repository of grants 
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface GrantRepository extends DefaultRepository<Grant> {

    /**
     * 
     * @param group
     * @return 
     */
    List<Grant> findByGroup(Group group);
}
