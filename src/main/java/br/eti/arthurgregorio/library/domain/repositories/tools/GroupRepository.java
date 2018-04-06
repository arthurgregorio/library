package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.security.Group;
import br.eti.arthurgregorio.library.domain.model.entities.security.Group_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import java.util.Optional;
import javax.persistence.metamodel.SingularAttribute;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

/**
 * The user groups repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface GroupRepository extends DefaultRepository<Group> {

    /**
     * 
     * @param name
     * @return 
     */
    Optional<Group> findOptionalByName(String name);

    /**
     * {@inheritDoc }
     * 
     * @param filter
     * @return 
     */
    @Override
    default Criteria<Group, Group> getRestrictions(String filter) {
        return this.criteria()
                .likeIgnoreCase(Group_.name, filter);
    }
    
    /**
     * {@inheritDoc }
     * 
     * @return 
     */
    @Override
    default SingularAttribute<Group, Boolean> getBlockedProperty() {
        return Group_.blocked;
    }
}
