package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.tools.Grant;
import br.eti.arthurgregorio.library.domain.model.entities.tools.Group;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

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
