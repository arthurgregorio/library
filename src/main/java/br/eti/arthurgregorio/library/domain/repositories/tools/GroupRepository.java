package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.tools.Group;
import br.eti.arthurgregorio.library.domain.model.entities.tools.Group_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Optional;

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
     * Find a {@link Group} by the name
     *
     * @param name the name of the {@link Group} to search
     * @return an {@link Optional} of the {@link Group}
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
    default SingularAttribute<Group, Boolean> getEntityStateProperty() {
        return Group_.active;
    }
    
    /**
     * {@inheritDoc }
     * 
     * @param criteria 
     */
    @Override
    default void setOrder(Criteria<Group, Group> criteria) {
        criteria.orderAsc(Group_.name);
    }
}
